package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSummary {
    private LocalDateTime transactionDate;
    private String transactionType;
    private String cryptoCoinName;
    private Double amountTransacted;
    private Double priceAtTransaction;
    private Double totalTransactionValue;

    public TransactionSummary(double totalBuy, double totalSell, double v) {
    }
}
