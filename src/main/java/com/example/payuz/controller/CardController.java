package com.example.payuz.controller;

import com.example.payuz.dto.requests.AddCardDto;
import com.example.payuz.payload.ApiResponse;
import com.example.payuz.services.CardService;
import com.example.payuz.dto.requests.DemoAddFundsDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
@Tag(name = "Card API", description = "Kredit kartalari bilan ishlash qismi")
public class CardController {

    private final CardService cardService;


    @PostMapping("/addCard")
    public ResponseEntity<ApiResponse> addCard(@RequestBody AddCardDto addCardDto) {

        ApiResponse apiResponse = cardService.addCard(addCardDto);

        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @GetMapping("/myCards")
    public ResponseEntity<ApiResponse> getMyCards() {
        ApiResponse apiResponse = cardService.getMyCards();

        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }


    @GetMapping("/getCard/{ids}")
    public ResponseEntity<ApiResponse> getCardId(@PathVariable UUID ids) {
        ApiResponse apiResponse = cardService.getCardById(ids);

        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @DeleteMapping("/deleteCard/{ids}")
    public ResponseEntity<ApiResponse> deleteCardById(@PathVariable UUID ids) {
        ApiResponse apiResponse = cardService.deleteCardById(ids);

        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @PostMapping("/demo/addFunds")
    public ResponseEntity<ApiResponse> demoAddFunds(@RequestBody DemoAddFundsDto demoAddFundsDto) {
        ApiResponse apiResponse = cardService.demoAddFunds(demoAddFundsDto);

        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }
}
