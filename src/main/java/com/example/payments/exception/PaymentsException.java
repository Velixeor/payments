package com.example.payments.exception;

import com.example.payments.dto.UserDTO;

public class PaymentsException extends RuntimeException {

    public PaymentsException(String message) {
        super(message);
    }
}
