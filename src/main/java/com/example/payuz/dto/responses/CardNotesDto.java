package com.example.payuz.dto.responses;

import com.example.payuz.enums.cards.CardType;

public record CardNotesDto(
        String cardNumber,
        String expiredDate,
        String cardHolderName,
        String cvv,
        CardType cardType,
        CardBalanceDto cardBalanceDto
) {}
