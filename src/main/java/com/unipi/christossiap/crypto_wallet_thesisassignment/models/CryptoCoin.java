package com.unipi.christossiap.crypto_wallet_thesisassignment.models;


import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cryptocoin")
public class CryptoCoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String symbol;
    private Long total_supply;
    private Double price;
    private Double percent_change_24h;
    private Double market_cap;
    private LocalDateTime last_updated;

    @ManyToMany
    @JoinTable(
            name = "cryptocoin_portfolio",
            joinColumns = @JoinColumn(name = "cryptocoin_id"),
            inverseJoinColumns = @JoinColumn(name = "portfolio_id")
    )
    private List<Portfolio> portfolios;


    @ManyToMany
    @JoinTable(
            name = "cryptocoin_transaction",
            joinColumns = @JoinColumn(name = "cryptocoin_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id")
    )
    private List<Transaction> transactions;
}
