package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Portfolio {
    @Id
    private Integer id;
    private Double balance;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PortfolioItem> portfolioItems = new ArrayList<>();

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", unique = true)
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

}
