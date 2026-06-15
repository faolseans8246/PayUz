package com.example.payuz.services;

import com.example.payuz.dto.requests.AddCardDto;
import com.example.payuz.payload.ApiResponse;

import java.util.UUID;

public interface CardService {

    ApiResponse addCard(AddCardDto addCardDto);
    ApiResponse getMyCards();
    ApiResponse getCardById(UUID cardId);
    ApiResponse deleteCardById(UUID cardId);
}
