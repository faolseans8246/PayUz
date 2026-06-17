package com.example.payuz.services.logikService;

import com.example.payuz.dto.TransactionHistoryDto;
import com.example.payuz.dto.requests.TransferRequestDto;
import com.example.payuz.entity.CardNotes;
import com.example.payuz.entity.TransactionHistory;
import com.example.payuz.enums.cards.TransferStatus;
import com.example.payuz.enums.cards.TransferType;
import com.example.payuz.payload.ApiResponse;
import com.example.payuz.repositories.CardRepository;
import com.example.payuz.repositories.TransactionHistoryRepository;
import com.example.payuz.services.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final CardRepository cardRepository;


    @Override
    public ApiResponse transfer(TransferRequestDto transferRequestDto) {

        CardNotes senderCard = cardRepository.findByCardNumber(transferRequestDto.senderCardNumber())
                .orElseThrow(() -> new RuntimeException("Jo'natuvchi karta topilmadi!"));

        CardNotes reseiverCard = cardRepository.findByCardNumber(transferRequestDto.reseiverCardNumber())
                .orElseThrow(() -> new RuntimeException("Qabul qiluvchi karta topilmadi!"));

        if (senderCard.getCardBalance().isCardBlocked()) {
            return new ApiResponse("Qabul qiluvchi karta blocklangan!", false, null);
        }

        if (reseiverCard.getCardBalance().isCardBlocked()) {
            return new ApiResponse("Qabul qilivchi karta blocklangan!", false, null);
        }

        if (senderCard.getCardNumber().equals(reseiverCard.getCardNumber())) {
            return new ApiResponse("Kartani o'ziga o'zi o'tkazish mavjud emas!", false, null);
        }

        TransferType transferType;
        BigDecimal percent;

        if (senderCard.getUserBase().getId().equals(reseiverCard.getUserBase().getId())) {
            transferType = TransferType.OWN_CARD;
            percent = new BigDecimal("0.5");
        } else {
            transferType = TransferType.OTHER_CARD;
            percent = new BigDecimal("1");
        }

        BigDecimal commission = transferRequestDto.amount().multiply(percent).divide(new BigDecimal("100"));
        BigDecimal totalAmount = transferRequestDto.amount().add(commission);

        if (senderCard.getCardBalance().getBalance().compareTo(totalAmount) < 0) {
            return new ApiResponse("Kartada mablag' yetarli emas", false, null);
        }

        senderCard.getCardBalance().setBalance(
                senderCard.getCardBalance().getBalance().subtract(totalAmount)
        );

        reseiverCard.getCardBalance().setBalance(
                reseiverCard.getCardBalance().getBalance().add(transferRequestDto.amount())
        );

        cardRepository.save(senderCard);
        cardRepository.save(reseiverCard);

        TransactionHistory history = new TransactionHistory();

        history.setSenderCard(senderCard);
        history.setReseiverCard(reseiverCard);
        history.setAmount(transferRequestDto.amount());
        history.setCommission(commission);
        history.setTotalAmount(totalAmount);
        history.setTransferType(transferType);
        history.setTransferStatus(TransferStatus.SUCCESS);
        history.setLocalDateTime(LocalDateTime.now());

        transactionHistoryRepository.save(history);

        Map<String, Object> response = new HashMap<>();

        response.put("amount", transferRequestDto.amount());
        response.put("commission", commission);
        response.put("totalAmount", totalAmount);
        response.put("transferType", transferType);


        return new ApiResponse("O'tkazmma muvaffaqiyatli amalga oshirildi", true, response);
    }


    @Override
    public ApiResponse getTransactionHistory(String cardNumber) {

        CardNotes card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Karta topilmadi!"));

        List<TransactionHistory> histories = transactionHistoryRepository.findBySenderCardOrReseiverCard(card, card);

        List<TransactionHistoryDto> result = histories.stream()
                .map(hist -> new TransactionHistoryDto(
                        hist.getSenderCard()
                                .getCardNumber(),

                        hist.getReseiverCard()
                                .getCardNumber(),

                        hist.getAmount(),
                        hist.getCommission(),
                        hist.getTotalAmount(),
                        hist.getTransferType().name(),
                        hist.getTransferStatus().name(),
                        hist.getLocalDateTime()
                )).toList();

        return new ApiResponse("Transaksiyalar tarixi muvaffaqiyatli olindi!", true, result);
    }
}
