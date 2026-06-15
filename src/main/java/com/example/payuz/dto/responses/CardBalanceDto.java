package com.example.payuz.dto.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardBalanceDto {

    private BigDecimal balance;
    private boolean cardBlocked = false;
}
