package com.example.payments.exception.userLoyaltyLevel;


import com.example.payments.dto.UserLoyaltyLevelDTO;
import com.example.payments.exception.PaymentsException;


public class UserLoyaltyLevelCreationException extends PaymentsException {
    public UserLoyaltyLevelCreationException(UserLoyaltyLevelDTO userLoyaltyLevelDTO) {
            super("Failed to create user loyalty level " + userLoyaltyLevelDTO);
    }

}
