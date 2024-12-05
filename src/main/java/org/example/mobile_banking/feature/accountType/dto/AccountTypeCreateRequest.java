package org.example.mobile_banking.feature.accountType.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
@Builder
public record AccountTypeCreateRequest(

        @NotBlank(message = "Alias is required")
        String alias,
        @NotBlank(message = "Name is required")
        String name,
        String description
) {
}
