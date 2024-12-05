package org.example.mobile_banking.feature.account.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountTransferLimitRequest( @NotNull(message = "Amount has required") @Min(1000) BigDecimal amount) {
}
