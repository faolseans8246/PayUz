package com.example.payuz.dto.requests;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class SignupRequest {

    @NotBlank(message = "Firstname is required")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    private String password;
}
