package com.example.payments.exception.userLoyaltyLevel;


import com.example.payments.dto.UserLoyaltyLevelDTO;
import com.example.payments.exception.PaymentsException;


public class UserLoyaltyLevelUpdateException extends PaymentsException {
    public UserLoyaltyLevelUpdateException(UserLoyaltyLevelDTO userLoyaltyLevelDTO) {
        super("Failed to update user loyalty level " + userLoyaltyLevelDTO);
    }
}
