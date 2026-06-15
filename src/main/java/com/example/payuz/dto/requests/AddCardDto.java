package com.example.payuz.dto.requests;

import com.example.payuz.enums.cards.CardType;
import lombok.Data;

@Data
public class AddCardDto {

    private String cardNumber;
    private String expiredDate;
    private String cvv;
    private String cardHolderName;
    private CardType cardType;
}
