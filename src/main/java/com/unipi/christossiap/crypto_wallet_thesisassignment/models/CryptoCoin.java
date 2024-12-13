package com.unipi.christossiap.crypto_wallet_thesisassignment.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema(
        name = "CryptoCoin",
        description = "The CryptoCoin used in Cryptio"
) //Για το Swagger-Documentation details...
public class CryptoCoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String symbol;
    private Long totalSupply;
    private Double price;
    private Double percentChange24h;
    private Double marketCap;
    private LocalDateTime lastUpdated;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinTable(
            name = "CryptoCoinPortfolio",
            joinColumns = @JoinColumn(name = "cryptocoin_id"),
            inverseJoinColumns = @JoinColumn(name = "portfolio_id")
    )
    private List<Portfolio> portfolios = new ArrayList<>();


    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "CryptoCoinTransaction",
            joinColumns = @JoinColumn(name = "cryptocoin_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id")
    )
    private List<Transaction> transactions = new ArrayList<>();


    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "CryptoCoinWatchList",
            joinColumns = @JoinColumn(name = "cryptocoin_id"),
            inverseJoinColumns = @JoinColumn(name = "watchlist_id")
    )
    private List<WatchList> watchLists = new ArrayList<>();
}
