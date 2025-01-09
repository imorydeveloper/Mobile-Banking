package org.example.mobile_banking.feature.auth;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile_banking.domain.Role;
import org.example.mobile_banking.domain.User;
import org.example.mobile_banking.domain.UserVerification;
import org.example.mobile_banking.feature.auth.dto.*;
import org.example.mobile_banking.feature.user.RoleRepository;
import org.example.mobile_banking.feature.user.UserRepository;
import org.example.mobile_banking.mapper.UserMapper;
import org.example.mobile_banking.util.RandomUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImp implements AuthService {

    private  final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JavaMailSender javaMailSender;
    private final UserVerificationRepository userVerificationRepository;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtEncoder accessTokenJwtEncoder;
    private final JwtEncoder refreshTokenJwtEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;



    //Inject email
    @Value("${spring.mail.username}")
    private String adminEmail;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        //validate user phone number
        if(userRepository.existsByPhoneNumber(registerRequest.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Phone number already exists");
        }

        //validate user email
        if(userRepository.existsByEmail(registerRequest.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already exists");
        }


        // validate user password
        if(!registerRequest.password().equals(registerRequest.confirmPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Passwords does not match");
        }

        // validate user national card id
        if(userRepository.existsByNationalCardId(registerRequest.nationalCardId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"National Card ID already exists");
        }

        // validate term and policy
        if(!registerRequest.acceptTerm()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Your must be accepted the term");
        }

        User user = userMapper.fromUserRegisterRequest(registerRequest);
        //set system data
        user.setUuid(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfileImage("profile/default-user.png");
        user.setIsBlocked(false);
        user.setIsDeleted(false);
        user.setIsVerified(false);
        // find role
        Role roleUser = roleRepository.findRoleUser(); //default role
        Role roleCustomer = roleRepository.findRoleCustomer();
        List<Role>roles = List.of(roleUser,roleCustomer);
        user.setRoles(roles);
        userRepository.save(user);

        return RegisterResponse.builder().message("You have register successfully , please verify an email!").email(user.getEmail()).build();
    }
    @Override
    public void sendVerification(String email) throws MessagingException {
        //validate email
        User user = userRepository.findByEmail(email).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User email has not been found"));
        UserVerification userVerification = new UserVerification();
        userVerification.setUser(user);
        userVerification.setVerifiedCode(RandomUtil.random6Digits());
        userVerification.setExpiryTime(LocalTime.now().plusMinutes(1));
        userVerificationRepository.save(userVerification);
        // Prepare email for sending
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setFrom(adminEmail);
        helper.setSubject("User Verification");
        helper.setText(userVerification.getVerifiedCode());

        javaMailSender.send(message);

    }

    @Override
    public void resendVerification(String email) throws MessagingException {
        //validate email
        User user = userRepository.findByEmail(email).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User email has not been found"));
        UserVerification userVerification = userVerificationRepository.findByUser(user).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User has not been found"));
        userVerification.setVerifiedCode(RandomUtil.random6Digits());
        userVerification.setExpiryTime(LocalTime.now().plusMinutes(1));
        userVerificationRepository.save(userVerification);
        // Prepare email for sending
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setFrom(adminEmail);
        helper.setSubject("User Verification");
        helper.setText(userVerification.getVerifiedCode());

        javaMailSender.send(message);

    }

    @Override
    public void verify(VerificationRequest verificationRequest) {
        // validate email
        User user = userRepository.findByEmail(verificationRequest.email()).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User email has not been found"));
        //validate verified code
        UserVerification userVerification = userVerificationRepository.findByUserAndVerifiedCode(user, verificationRequest.verifiedCode()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User verification has not been found"));
        // Is verify code expired?
        if(LocalTime.now().isAfter(userVerification.getExpiryTime())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Verify code has expired");
        }
        user.setIsVerified(true);
        userRepository.save(user);
        userVerificationRepository.delete(userVerification);


    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return null;
    }

}
