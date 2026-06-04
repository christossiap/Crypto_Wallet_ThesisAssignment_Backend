package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs;

import com.unipi.christossiap.crypto_wallet_thesisassignment.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionRequest(
        @NotBlank String cryptoCoinName,
        @NotNull @Positive Double amount,
        @NotNull TransactionType transactionType
) {}
