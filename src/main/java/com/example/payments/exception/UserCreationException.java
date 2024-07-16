package com.example.payments.exception;


import com.example.payments.dto.UserDTO;


public class UserCreationException extends PaymentsException {
    public UserCreationException(UserDTO userDTO) {
        super("Failed to create user " + userDTO);
    }
}
