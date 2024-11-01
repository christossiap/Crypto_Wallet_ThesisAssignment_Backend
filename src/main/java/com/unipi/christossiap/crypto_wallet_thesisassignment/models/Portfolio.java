package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double amount; // Amount of a specific crypto the user holds

    @ManyToMany(mappedBy = "portfolios")
    private List<CryptoCoin> cryptoCoins;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "portfolio")
    private List<Transaction> transaction = new ArrayList<>();
}
