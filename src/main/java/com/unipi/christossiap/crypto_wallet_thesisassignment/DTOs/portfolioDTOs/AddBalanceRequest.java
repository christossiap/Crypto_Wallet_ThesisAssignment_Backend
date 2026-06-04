package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.portfolioDTOs;

import jakarta.validation.constraints.*;

public record AddBalanceRequest(
        @NotNull(message = "Balance is required")
        @Min(value = 5, message = "Minimum balance is 5.0")
        @Max(value = 1000, message = "Maximum balance is 1000.0")
        Double balance

) {
}