package com.example.payuz.repositories;

import com.example.payuz.entity.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SmsRepository extends JpaRepository<SmsCode, UUID> {

    @Query("select s from SmsCode s where s.phoneNumber = :phoneNumber and s.used = false order by s.createdAt desc")
    Optional<SmsCode> findLatestActiveCode(String phoneNumber);
}
