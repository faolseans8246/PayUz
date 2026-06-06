package com.example.payuz.exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends AppException {

    public AlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
