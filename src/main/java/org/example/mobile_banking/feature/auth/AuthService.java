package org.example.mobile_banking.feature.auth;

import jakarta.mail.MessagingException;
import org.example.mobile_banking.feature.auth.dto.RegisterRequest;
import org.example.mobile_banking.feature.auth.dto.RegisterResponse;
import org.example.mobile_banking.feature.auth.dto.VerificationRequest;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    void sendVerification(String email) throws MessagingException;
    void resendVerification(String email) throws MessagingException;
    void verify(VerificationRequest verificationRequest);
}
