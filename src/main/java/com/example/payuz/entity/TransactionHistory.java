package com.example.payuz.entity;

import com.example.payuz.enums.cards.TransferStatus;
import com.example.payuz.enums.cards.TransferType;
import com.example.payuz.index.Ids;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "transaction_history")
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory extends Ids {

    @ManyToOne
    @JoinColumn(name = "sender_card_id")
    private CardNotes senderCard;

    @ManyToOne
    @JoinColumn(name = "reseiver_card_id")
    private CardNotes reseiverCard;

    private BigDecimal amount;

    private BigDecimal commission;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private TransferType transferType;

    @Enumerated(EnumType.STRING)
    private TransferStatus transferStatus;

    private LocalDateTime localDateTime;
}
