package com.example.payuz.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @NotBlank(message = "Firstname is required") String firstName,
        @NotBlank(message = "Lastname is required") String lastName,
        @NotBlank(message = "Phone number is required") String phoneNumber,
        @NotBlank(message = "Password is required") String password
) {}
