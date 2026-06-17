package com.example.payuz.repositories;

import com.example.payuz.entity.CardNotes;
import com.example.payuz.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, UUID> {

    List<TransactionHistory> findBySenderCard(CardNotes senderCard);
    List<TransactionHistory> findByReseiverCard(CardNotes reseiverCard);
    List<TransactionHistory> findBySenderCardOrReseiverCard(CardNotes senderCard, CardNotes reseiverCard);
}
