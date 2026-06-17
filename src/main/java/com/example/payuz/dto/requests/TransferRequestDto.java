package com.example.payuz.dto.requests;

import java.math.BigDecimal;

public record TransferRequestDto(String senderCardNumber, String reseiverCardNumber, BigDecimal amount) {}
