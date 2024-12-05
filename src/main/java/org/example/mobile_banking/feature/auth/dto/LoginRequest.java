package org.example.mobile_banking.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotBlank(message = "Phone number is required")
        String phoneNumber,
        @NotNull(message = "Password is required")
        String password

) {
}
