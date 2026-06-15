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
        cardNotes.setCardNumber(addCardDto.getCardNumber());
        cardNotes.setCardHolderName(addCardDto.getCardHolderName());
        cardNotes.setCvv(addCardDto.getCvv());
        cardNotes.setExpiredDate(addCardDto.getExpiredDate());
        cardNotes.setCardType(addCardDto.getCardType());

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


    // Cartaning foydalanuvchi qismini shaqkllantirib chiqish qismi
    private UserBase getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return userRepository.findByPhoneNumber(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("Foydalanuvchi topilmadi!"));
    }

    private CardNotesDto toDto(CardNotes cardNotes) {

        CardBalanceDto balanceDto = new CardBalanceDto();
        balanceDto.setBalance(cardNotes.getCardBalance().getBalance());
        balanceDto.setCardBlocked(cardNotes.getCardBalance().isCardBlocked());

        CardNotesDto cardNotesDto = new CardNotesDto();
        cardNotesDto.setCardNumber(cardNotes.getCardNumber());
        cardNotesDto.setCardHolderName(cardNotes.getCardHolderName());
        cardNotesDto.setExpiredDate(cardNotes.getExpiredDate());
        cardNotesDto.setCvv(cardNotes.getCvv());
        cardNotesDto.setCardType(cardNotes.getCardType());
        cardNotesDto.setCardBalanceDto(balanceDto);

        return cardNotesDto;
    }
}
