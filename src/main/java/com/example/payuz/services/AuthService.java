package com.example.payuz.services;

import com.example.payuz.dto.requests.LoginRequest;
import com.example.payuz.payload.ApiResponse;

public interface AuthService {

    ApiResponse register(LoginRequest loginRequest);
    ApiResponse login(LoginRequest loginRequest);

}
