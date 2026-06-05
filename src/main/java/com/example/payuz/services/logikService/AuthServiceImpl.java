package com.example.payuz.services.logikService;

import com.example.payuz.dto.requests.LoginRequest;
import com.example.payuz.payload.ApiResponse;
import com.example.payuz.repositories.SmsRepository;
import com.example.payuz.repositories.UserRepository;
import com.example.payuz.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final SmsRepository smsRepository;


    @Override
    public ApiResponse register(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public ApiResponse login(LoginRequest loginRequest) {
        return null;
    }
}
