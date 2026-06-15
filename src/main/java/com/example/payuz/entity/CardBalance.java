package com.example.payuz.entity;

import com.example.payuz.index.Ids;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Card_Balance")
public class CardBalance extends Ids {

    private BigDecimal balance;
    private boolean cardBlocked = false;
}
