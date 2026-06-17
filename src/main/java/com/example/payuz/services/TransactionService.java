package com.example.payuz.services;

import com.example.payuz.dto.requests.TransferRequestDto;
import com.example.payuz.payload.ApiResponse;

public interface TransactionService {

    ApiResponse transfer(TransferRequestDto transferRequestDto);
    ApiResponse getTransactionHistory(String cardNumber);
}
