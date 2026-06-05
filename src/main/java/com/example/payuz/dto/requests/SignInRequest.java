package com.example.payuz.dto.requests;

import lombok.Data;

@Data
public class SignInRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
}
