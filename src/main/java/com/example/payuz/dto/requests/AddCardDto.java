package com.example.payuz.dto.requests;

import com.example.payuz.enums.cards.CardType;

public record AddCardDto(
        String cardNumber,
        String expiredDate,
        String cvv,
        String cardHolderName,
        CardType cardType)
{}
