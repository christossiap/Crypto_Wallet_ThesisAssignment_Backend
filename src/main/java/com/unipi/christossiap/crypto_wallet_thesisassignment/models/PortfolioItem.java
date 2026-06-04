package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PortfolioItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double coinAmount;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    @JsonBackReference
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "cryptocoin_id")
    @JsonBackReference
    private CryptoCoin cryptoCoin;
}
