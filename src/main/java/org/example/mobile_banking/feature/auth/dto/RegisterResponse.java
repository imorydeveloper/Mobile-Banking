package org.example.mobile_banking.feature.auth.dto;

import lombok.Builder;
import lombok.Data;

@Builder
public record RegisterResponse(

        String message,
        String email
) {
}
