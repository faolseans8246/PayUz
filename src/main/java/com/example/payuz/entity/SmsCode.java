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
@Table(name = "sms_codes")
@NoArgsConstructor
@AllArgsConstructor
public class SmsCode extends Ids {

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "sms_code", nullable = false)
    private String code;

    @Column(name = "expire_time", nullable = false)
    private Timestamp expireTime;

    @Column(nullable = false)
    @Builder.Default
    private boolean used = false;
}
