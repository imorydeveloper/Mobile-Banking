package org.example.mobile_banking.feature.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountCreateRequest(

        @NotBlank(message = "Account number is required")
        String actNo,
        @NotNull(message = "Balance is required")
        @Positive
        BigDecimal balance,
        @NotBlank(message = "Account Type is required")
        String accountType,
        @NotBlank(message = "Account owner is required")
        String userUuid

) {

}
