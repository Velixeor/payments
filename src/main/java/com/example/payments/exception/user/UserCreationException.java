package com.example.payments.exception.user;


import com.example.payments.dto.UserDTO;
import com.example.payments.exception.PaymentsException;


public class UserCreationException extends PaymentsException {
    public UserCreationException(UserDTO userDTO) {
        super("Failed to create user " + userDTO);
    }
}
