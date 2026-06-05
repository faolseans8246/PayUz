package com.example.payuz.dto.responses;

import com.example.payuz.enums.user.UserRole;
import com.example.payuz.enums.user.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponseDto {

    private UUID id;

    private String firstName;
    private String lastName;

    private String phoneNumber;
    private boolean phoneVerified;

    private UserRole role;
    private UserStatus status;
}
