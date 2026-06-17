package com.example.payuz.repositories;

import com.example.payuz.entity.CardNotes;
import com.example.payuz.entity.UserBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardNotes, UUID> {
    List<CardNotes> findAllByUserBase(UserBase userBase);
    Optional<CardNotes> findByCardNumber(String cardNumber);
}
