package org.example.mobile_banking.feature.auth;

import jakarta.mail.MessagingException;
import org.example.mobile_banking.feature.auth.dto.*;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    void sendVerification(String email) throws MessagingException;
    void resendVerification(String email) throws MessagingException;
    void verify(VerificationRequest verificationRequest);
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
