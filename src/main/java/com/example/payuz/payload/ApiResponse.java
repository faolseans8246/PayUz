package com.example.payuz.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private String message;
    private boolean success = false;
    private Object data;

    private ApiResponse(String message) {
        this.message = message;
        this.success = true;
    }

    private ApiResponse(Object data) {
        this.data = data;
        this.success = true;
        this.message = "Operation successful";
    }

    private ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
