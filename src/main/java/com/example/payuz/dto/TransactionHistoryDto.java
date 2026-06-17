package com.example.payuz.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionHistoryDto(
        String senderCard,
        String receiverCard,
        BigDecimal amount,
        BigDecimal commission,
        BigDecimal totalAmount,
        String transformerType,
        String transactionStatus,
        LocalDateTime createAt
) {}
