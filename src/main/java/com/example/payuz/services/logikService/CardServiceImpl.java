package com.example.payuz.services.logikService;

import com.example.payuz.dto.requests.AddCardDto;
import com.example.payuz.dto.responses.CardBalanceDto;
import com.example.payuz.dto.responses.CardNotesDto;
import com.example.payuz.entity.CardBalance;
import com.example.payuz.entity.CardNotes;
import com.example.payuz.entity.UserBase;
import com.example.payuz.exceptions.NotFoundException;
import com.example.payuz.payload.ApiResponse;
import com.example.payuz.repositories.CardRepository;
import com.example.payuz.repositories.UserRepository;
import com.example.payuz.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;


    @Override
    public ApiResponse addCard(AddCardDto addCardDto) {

        UserBase currentUser = getCurrentUser();

        CardBalance cardBalance = new CardBalance();
        cardBalance.setBalance(BigDecimal.ZERO);
        cardBalance.setCardBlocked(false);

        CardNotes cardNotes = new CardNotes();
        cardNotes.setCardNumber(addCardDto.cardNumber());
        cardNotes.setCardHolderName(addCardDto.cardHolderName());
        cardNotes.setCvv(addCardDto.cvv());
        cardNotes.setExpiredDate(addCardDto.expiredDate());
        cardNotes.setCardType(addCardDto.cardType());

        cardNotes.setCardBalance(cardBalance);
        cardNotes.setUserBase(currentUser);

        CardNotes cardNotesSaved = cardRepository.save(cardNotes);

        return new ApiResponse("Card notes added successfully", true, cardNotesSaved);
    }

    @Override
    public ApiResponse getMyCards() {

        UserBase curentUser = getCurrentUser();

        List<CardNotesDto> cardNotesDtos = cardRepository
                .findAllByUserBase(curentUser)
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse("Sizning kartalaringiz", true, cardNotesDtos);
    }

    @Override
    public ApiResponse getCardById(UUID cardId) {

        UserBase currentUser = getCurrentUser();

        CardNotes cardNotes = cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Karta topilmadi!"));

        if (!cardNotes.getUserBase().getId().equals(currentUser.getId())) {
            return new ApiResponse("BU karta egasi emassiz!", false, null);
        }

        return new ApiResponse("Karta topildi", true, cardNotes);
    }

    @Override
    public ApiResponse deleteCardById(UUID cardId) {

        UserBase currentUser = getCurrentUser();

        CardNotes cardNotes = cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Bu karta mavjud emas"));

        if (!cardNotes.getUserBase().getId().equals(currentUser.getId())) {
            return new ApiResponse("Bu kartani o'chirish huquqiga ega emassiz", false, null);
        }

        cardRepository.delete(cardNotes);

        return new ApiResponse("Karta muvaffaqiyatli o'chirildi!", true, cardNotes);
    }

    @Override
    public ApiResponse demoAddFunds(com.example.payuz.dto.requests.DemoAddFundsDto demoAddFundsDto) {

        // Find card by card number
        CardNotes cardNotes = cardRepository.findByCardNumber(demoAddFundsDto.cardNumber())
                .orElseThrow(() -> new NotFoundException("Karta topilmadi!"));

        CardBalance cardBalance = cardNotes.getCardBalance();
        if (cardBalance == null) {
            cardBalance = new CardBalance();
            cardBalance.setBalance(BigDecimal.ZERO);
            cardBalance.setCardBlocked(false);
            cardNotes.setCardBalance(cardBalance);
        }

        if (demoAddFundsDto.amount() == null) {
            return new ApiResponse("Miqdor berilmagan", false, null);
        }

        BigDecimal current = cardBalance.getBalance() == null ? BigDecimal.ZERO : cardBalance.getBalance();
        BigDecimal updated = current.add(demoAddFundsDto.amount());

        cardBalance.setBalance(updated);

        CardNotes saved = cardRepository.save(cardNotes);

        return new ApiResponse("Demo mablag' muvaffaqiyatli qo'shildi", true, saved.getCardBalance());
    }


    // Cartaning foydalanuvchi qismini shaqkllantirib chiqish qismi
    private UserBase getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return userRepository.findByPhoneNumber(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("Foydalanuvchi topilmadi!"));
    }

    private CardNotesDto toDto(CardNotes cardNotes) {
        CardBalanceDto balanceDto = new CardBalanceDto(
            cardNotes.getCardBalance().getBalance(),
            cardNotes.getCardBalance().isCardBlocked()
        );

        CardNotesDto cardNotesDto = new CardNotesDto(
            cardNotes.getCardNumber(),
            cardNotes.getExpiredDate(),
            cardNotes.getCardHolderName(),
            cardNotes.getCvv(),
            cardNotes.getCardType(),
            balanceDto
        );

        return cardNotesDto;
    }
}
