package org.example.mobile_banking.feature.accountType.dto;

import lombok.Builder;

@Builder
public record AccountTypeResponse(
    String alias,
    String name,
    String description,
    Boolean isDeleted

){
}
