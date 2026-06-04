package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCoinHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double price;
    private Double percentChange24h;
    private Double marketCap;
    private LocalDateTime lastUpdated;
    private Long totalSupply;

    @ManyToOne
    @JoinColumn(name = "cryptocoin_id")
    @JsonBackReference
    private CryptoCoin cryptoCoin;
}
