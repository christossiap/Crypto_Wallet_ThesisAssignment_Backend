package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Portfolio {
    @Id
    private Integer id;

    private Double balance;

    private Double coinAmount;

    @ManyToMany(mappedBy = "portfolios")
    private List<CryptoCoin> cryptoCoins = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "portfolio")
    private List<Transaction> transaction = new ArrayList<>();
}
