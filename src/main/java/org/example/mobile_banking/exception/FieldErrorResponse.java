package org.example.mobile_banking.exception;

import lombok.Builder;

@Builder
public record FieldErrorResponse(
            String field,
            String detail
){

}
