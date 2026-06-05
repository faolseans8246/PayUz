package com.example.payuz.entity;


import com.example.payuz.index.Ids;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@Table(name = "SmsCodes")
@NoArgsConstructor
@AllArgsConstructor
public class SmsCode extends Ids {

    @Column(name = "Phone number")
    private String phoneNumber;

    @Column(name = "SMS code")
    private String code;

    @Column(nullable = false)
    private Timestamp expireTime;

    @Builder.Default
    private boolean used = false;
}
