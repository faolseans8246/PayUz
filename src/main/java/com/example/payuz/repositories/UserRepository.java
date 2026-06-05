package com.example.payuz.repositories;

import com.example.payuz.entity.UserBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserBase, UUID> {

    Optional<UserBase> userBaseByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);
}
