package org.example.mobile_banking.feature.accountType.dto;


public record AccountTypeUpdateRequest(
        String description,
        Boolean isDeleted
) {
}
