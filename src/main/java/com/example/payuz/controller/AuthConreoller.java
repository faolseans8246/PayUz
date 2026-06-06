package com.example.payuz.controller;

import com.example.payuz.dto.requests.LoginRequest;
import com.example.payuz.dto.requests.SignupRequest;
import com.example.payuz.dto.requests.VerifySmsRequest;
import com.example.payuz.payload.ApiResponse;
import com.example.payuz.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth API", description = "Authentication and registration endpoints")
public class AuthConreoller {

    private final AuthService authService;

    @Operation(summary = "Login", description = "Authenticate by phone and password")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        ApiResponse apiResponse = authService.login(loginRequest);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }


    @Operation(summary = "Register", description = "Register a new user")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody SignupRequest signupRequest) {

        ApiResponse apiResponse = authService.register(signupRequest);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }


    @Operation(summary = "Verify SMS", description = "Verify SMS code and activate account")
    @PostMapping("/verify-sms")
    public ResponseEntity<ApiResponse> verifySms(@Valid @RequestBody VerifySmsRequest verifySmsRequest) {

        ApiResponse apiResponse = authService.verifySms(verifySmsRequest);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }
}
