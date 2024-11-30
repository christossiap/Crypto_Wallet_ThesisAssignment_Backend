package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations.CryptoCoinTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sound.sampled.Port;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double amount;
    private Double priceAtTransaction;
    private LocalDateTime transactionDate;
    private String transactionType; // BUY, SELL, TRANSFER

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToMany(mappedBy = "transactions")
    @JsonIgnore
    private List<CryptoCoin> cryptoCoins = new ArrayList<>();

    public void addCryptoCoin(CryptoCoin cryptoCoin){
        if (!cryptoCoins.contains(cryptoCoin)) {
            cryptoCoins.add(cryptoCoin);
        }
        if (!cryptoCoin.getTransactions().contains(this)) {
            cryptoCoin.getTransactions().add(this);
        }
    }

}
