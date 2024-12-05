package org.example.mobile_banking.feature.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Phone number is required")
        @Size(min = 9,max = 10,message = "Phone number must be between 9 to 10 digits")
        String phoneNumber,
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"
        ,message = "Password must contain minimum characters in length, At least one uppercase English letter, At least one lowercase English letter, At least one digit, At least one special character."
        )//Regular Expression for validate Strong Password
        String password,
        @NotBlank(message = "Confirm password is required")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"
         ,message = "Password must contain minimum characters in length, At least one uppercase English letter, At least one lowercase English letter, At least one digit, At least one special character."
        )
        String confirmPassword,
        @NotBlank(message = "Pin is required")
        @Size(min=4,max = 4,message = "PIN must be only 4 digits")
        String pin,
        @NotBlank(message = "National id card is required")
        String nationalCardId,
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Gender is required")
        String gender,
        @NotNull(message = "Term must be accepted")
        Boolean acceptTerm
) {
}
