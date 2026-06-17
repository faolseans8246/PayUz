package com.example.payuz.dto.responses;

import java.math.BigDecimal;

public record CardBalanceDto(BigDecimal balance, boolean cardBlocked) {
    public CardBalanceDto {
        // default cardBlocked to false when null/omitted via constructor calls
    }
}
