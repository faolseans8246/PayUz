package com.example.payuz.dto.responses;

import com.example.payuz.enums.cards.CardType;
import lombok.Data;

@Data
public class CardNotesDto {

    private String cardNumber;
    private String expiredDate;
    private String cardHolderName;
    private String cvv;
    private CardType cardType;

    private CardBalanceDto cardBalanceDto;
}
