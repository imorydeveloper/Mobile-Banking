package org.example.mobile_banking.feature.account.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountRenameRequest(
        @NotBlank(message = "Alias is required")
        String alias
) {
}
