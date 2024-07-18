package com.example.payments.exception.user;


import com.example.payments.dto.UserDTO;
import com.example.payments.exception.PaymentsException;


public class UserUpdateException extends PaymentsException {
    public UserUpdateException(UserDTO userDTO) {
        super("Failed to create user " + userDTO);
    }
}
