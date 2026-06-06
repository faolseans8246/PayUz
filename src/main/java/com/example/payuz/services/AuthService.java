package com.example.payuz.services;

import com.example.payuz.dto.requests.LoginRequest;
import com.example.payuz.dto.requests.SignupRequest;
import com.example.payuz.dto.requests.VerifySmsRequest;
import com.example.payuz.payload.ApiResponse;

public interface AuthService {

    ApiResponse register(SignupRequest signupRequest);

    ApiResponse verifySms(VerifySmsRequest verifySmsRequest);

    ApiResponse login(LoginRequest loginRequest);

}
