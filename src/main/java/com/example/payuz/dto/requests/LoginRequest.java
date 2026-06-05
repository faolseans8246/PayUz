package com.example.payuz.dto.requests;

import lombok.Data;

@Data
public class LoginRequest {

    private String phoneNumber;
    private String password;
}
