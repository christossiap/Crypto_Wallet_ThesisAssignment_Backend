package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    private Integer id;
    private Integer userId; // Foreign key to User
    private Integer coinId; // Foreign key to CryptoCoin
    private Double amount;
    private Double priceAtTransaction;
    private LocalDateTime transactionDate;
    private String transactionType; // BUY, SELL, TRANSFER
}
