package com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}