package org.example.mobile_banking.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token is require")
        String refreshToken

) {
}
