package com.example.payuz.dto.requests;

import lombok.Data;

@Data
public class VerifySmsRequest {

    private String phineNumber;

    private String code;
}
