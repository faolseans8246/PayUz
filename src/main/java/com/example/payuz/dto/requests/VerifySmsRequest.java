package com.example.payuz.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record VerifySmsRequest(
        @NotBlank(message = "Phone number is required") String phoneNumber,
        @NotBlank(message = "Code is required") String code
) {}
