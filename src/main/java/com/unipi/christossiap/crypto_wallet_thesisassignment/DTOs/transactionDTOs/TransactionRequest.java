package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private String transactionType; // BUY or SELL
    private String cryptoCoinName; // Name of the coin
    private Double amount; // Amount of coin to buy/sell
}
