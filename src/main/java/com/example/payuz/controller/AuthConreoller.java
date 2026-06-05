package com.example.payuz.controller;

import com.example.payuz.dto.requests.LoginRequest;
import com.example.payuz.payload.ApiResponse;
import com.example.payuz.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthConreoller {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {

        ApiResponse apiResponse = authService.login(loginRequest);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody LoginRequest loginRequest) {

        ApiResponse apiResponse = authService.register(loginRequest);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }
}
