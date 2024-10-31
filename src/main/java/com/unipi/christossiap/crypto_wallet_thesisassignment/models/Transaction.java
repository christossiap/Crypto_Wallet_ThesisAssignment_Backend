package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations.CryptoCoinTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sound.sampled.Port;
import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToMany(mappedBy = "transaction")
    private List<CryptoCoin> cryptoCoins;

}
