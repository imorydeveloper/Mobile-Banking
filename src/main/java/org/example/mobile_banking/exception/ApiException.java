package org.example.mobile_banking.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiException {



    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSize;
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    Map<?,?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        ErrorResponse<?> errorResponse = ErrorResponse.builder()
                .code(e.getStatusCode().value())
                .reason(e.getMessage() + ": "+maxSize)
                .build();
        return Map.of("error", errorResponse);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<?,?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        List<FieldErrorResponse>fieldErrors = new ArrayList<>();
        e.getFieldErrors().forEach(
                /*fieldError -> {

                    fieldErrors.add(new FieldError(fieldError.getField(), fieldError.getDefaultMessage()));
                }*/
                fieldError -> fieldErrors.add(FieldErrorResponse.builder()
                                .field(fieldError.getField())
                                .detail(fieldError.getDefaultMessage())
                        .build())
        );

        return Map.of("error",ErrorResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .reason(fieldErrors)
                .build());
    }



    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleResponseException(ResponseStatusException e){
        System.out.println("handleResponseStatusException: "+e.getMessage());
        ErrorResponse <String> errorResponse = ErrorResponse.<String>builder()
                .code(e.getStatusCode().value())
                .reason(e.getReason())
                .build();
        return ResponseEntity.status(e.getStatusCode()).body(Map.of("error",errorResponse));
    }
}
