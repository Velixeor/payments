package com.example.payments.exception.user;


import com.example.payments.exception.PaymentsException;


public class UserDeleteException extends PaymentsException {
    public UserDeleteException(String message) {
        super(message);
    }
}
