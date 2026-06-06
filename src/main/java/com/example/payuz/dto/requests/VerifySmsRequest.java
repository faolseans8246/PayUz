package com.example.payuz.dto.requests;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class VerifySmsRequest {

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Code is required")
    private String code;
}
