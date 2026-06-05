package com.example.payuz.repositories;

import com.example.payuz.entity.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SmsRepository extends JpaRepository<SmsCode, UUID> {

    Optional<SmsCode> findByPhoneNumber(String phoneNumber);
}
