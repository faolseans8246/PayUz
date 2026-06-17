package com.example.payuz.dto.responses;

import com.example.payuz.enums.user.UserRole;
import com.example.payuz.enums.user.UserStatus;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String firstName,
        String lastName,
        String phoneNumber,
        boolean phoneVerified,
        UserRole role,
        UserStatus status
) {}
