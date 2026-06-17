package com.example.payuz.controller;

import com.example.payuz.dto.requests.TransferRequestDto;
import com.example.payuz.payload.ApiResponse;
import com.example.payuz.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transfer")
public class TransferController {

    private final TransactionService transactionService;


    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse> transfer(@Valid @RequestBody TransferRequestDto transferRequestDto) {
        ApiResponse apiResponse = transactionService.transfer(transferRequestDto);

        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }


    @GetMapping("/history/{cardNumber}")
    public ResponseEntity<ApiResponse> history(@PathVariable String cardNumber) {
        ApiResponse apiResponse = transactionService.getTransactionHistory(cardNumber);

        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }
}
