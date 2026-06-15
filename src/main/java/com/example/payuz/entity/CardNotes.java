package com.example.payuz.entity;

import com.example.payuz.enums.cards.CardType;
import com.example.payuz.index.Ids;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Credit_Card_Notes")
public class CardNotes extends Ids {

    private String cardNumber;
    private String cardHolderName;
    private String expiredDate;
    private String cvv;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "balance_id")
    private CardBalance cardBalance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserBase userBase;
}
