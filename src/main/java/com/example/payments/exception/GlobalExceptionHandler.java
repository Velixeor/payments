package com.example.payments.exception;


import com.example.payments.exception.user.UserCreationException;
import com.example.payments.exception.user.UserDeleteException;
import com.example.payments.exception.user.UserUpdateException;
import com.example.payments.exception.userLoyaltyLevel.UserLoyaltyLevelCreationException;
import com.example.payments.exception.userLoyaltyLevel.UserLoyaltyLevelUpdateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<Void> handleUserCreationException(UserCreationException userCreationException) {
        log.warn(userCreationException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<Void> handleUserUpdateException(UserUpdateException userUpdateException) {
        log.warn(userUpdateException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(UserDeleteException.class)
    public ResponseEntity<Void> handleUserDeleteException(UserDeleteException userDeleteException) {
        log.warn(userDeleteException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    @ExceptionHandler(UserLoyaltyLevelUpdateException.class)
    public ResponseEntity<Void> handleUserLoyaltyLevelUpdateException(UserLoyaltyLevelUpdateException userLoyaltyLevelUpdateException) {
        log.warn(userLoyaltyLevelUpdateException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    @ExceptionHandler(UserLoyaltyLevelCreationException.class)
    public ResponseEntity<Void> handleUserLoyaltyLevelCreationException(UserLoyaltyLevelCreationException userLoyaltyLevelCreationException) {
        log.warn(userLoyaltyLevelCreationException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(PaymentsException.class)
    public ResponseEntity<Void> handlePaymentsException(PaymentsException paymentsException) {
        log.warn("Unhandled Exception. " + paymentsException.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
