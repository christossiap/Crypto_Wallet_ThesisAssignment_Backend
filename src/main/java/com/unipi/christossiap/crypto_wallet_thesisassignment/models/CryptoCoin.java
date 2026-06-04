package com.unipi.christossiap.crypto_wallet_thesisassignment.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "cryptoCoin", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CryptoCoinHistory> cryptoCoinHistories = new ArrayList<>();

    @OneToMany(mappedBy = "cryptoCoin")
    private List<PortfolioItem> portfolioItems = new ArrayList<>();


    @OneToMany(mappedBy = "cryptoCoin", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Transaction> transactions;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "CryptoCoinWatchList",
            joinColumns = @JoinColumn(name = "cryptocoin_id"),
            inverseJoinColumns = @JoinColumn(name = "watchlist_id")
    )
    private List<WatchList> watchLists = new ArrayList<>();
}
