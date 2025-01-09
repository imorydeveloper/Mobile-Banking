package org.example.mobile_banking.feature.auth.dto;

import lombok.Builder;

@Builder
public record AuthResponse(

        // Token type
        String tokenType,
        String accessToken,
        String refreshToken
) {
}
