package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs;
import com.unipi.christossiap.crypto_wallet_thesisassignment.enums.TransactionType;

import java.time.LocalDateTime;

public record TransactionSummary(

        Integer id,
        Double amount,
        Double priceAtTransaction,
        LocalDateTime transactionDate,
        String cryptoCoinName,
        TransactionType transactionType,
        Double totalTransactionAmount

) {
}
