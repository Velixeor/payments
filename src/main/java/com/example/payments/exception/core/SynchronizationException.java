package com.example.payments.exception.core;


import com.example.payments.exception.PaymentsException;


public class SynchronizationException extends PaymentsException {
    public SynchronizationException() {
        super("Failed to synchronization  core");
    }
}
