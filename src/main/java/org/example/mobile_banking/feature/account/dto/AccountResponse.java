package org.example.mobile_banking.feature.account.dto;

import lombok.Builder;
import org.example.mobile_banking.feature.accountType.dto.AccountTypeResponse;

import java.math.BigDecimal;

@Builder
public record AccountResponse(

        String alias,
        String actName,
        String actNo,
        BigDecimal balance,
        AccountTypeResponse accountType

) {
}
