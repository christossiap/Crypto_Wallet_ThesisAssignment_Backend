package com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions;

public class InsufficientCoinsException extends RuntimeException {
    public InsufficientCoinsException(String message) {
        super(message);
    }
}