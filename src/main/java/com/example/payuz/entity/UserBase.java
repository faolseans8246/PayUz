package com.example.payuz.entity;

import com.example.payuz.enums.user.UserRole;
import com.example.payuz.enums.user.UserStatus;
import com.example.payuz.index.Ids;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
public class UserBase extends Ids {

    @Column(nullable = false, name = "Firstname")
    private String firstName;

    @Column(nullable = false, name = "Lastname")
    private String lastName;

    @Column(nullable = false, name = "Phone Number", unique = true)
    private String phoneNumber;

    @Column(nullable = false, name = "Password")
    private String password;

    private boolean phoneVerified = false;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
