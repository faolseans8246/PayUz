package com.example.payuz.dto.responses;

import java.math.BigDecimal;

public record TransferReponseDto(String message, BigDecimal amount, BigDecimal commission, BigDecimal totalAmount) {}
