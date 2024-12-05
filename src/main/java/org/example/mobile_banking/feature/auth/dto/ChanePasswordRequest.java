package org.example.mobile_banking.feature.auth.dto;

public record ChanePasswordRequest(

        String oldPassword,
        String password,
        String confirmPassword
) {
}
