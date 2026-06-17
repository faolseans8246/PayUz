package com.example.payuz.dto.requests;

import java.math.BigDecimal;

public record DemoAddFundsDto(
        String cardNumber,
        BigDecimal amount
) {
}
