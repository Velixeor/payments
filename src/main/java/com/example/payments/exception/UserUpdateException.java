package com.example.payments.exception;

import com.example.payments.dto.UserDTO;

public class UserUpdateException extends PaymentsException{
    public UserUpdateException(UserDTO userDTO) {
        super("Failed to create user " + userDTO );
    }
}
