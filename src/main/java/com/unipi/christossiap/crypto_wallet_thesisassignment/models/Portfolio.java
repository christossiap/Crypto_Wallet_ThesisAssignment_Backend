package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @Min(value = 5, message = "Ελάχιστη κατάθεση 5 ευρώ!")
    @Max(value = 10000,message = "Μέγιστη κατάθεση 10000 ευρώ αν συναλλαγή")
    private Double balance;

    @ManyToMany(mappedBy = "portfolios")
    private List<CryptoCoin> cryptoCoins = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "portfolio")
    private List<Transaction> transactions = new ArrayList<>();

    public void addCryptoCoin(CryptoCoin cryptoCoin){
        if (!cryptoCoins.contains(cryptoCoin)) {
            cryptoCoins.add(cryptoCoin);
        }
        if (!cryptoCoin.getPortfolios().contains(this)) {
            cryptoCoin.getPortfolios().add(this);
        }
    }
    public void removeCryptoCoin(CryptoCoin cryptoCoin){
        cryptoCoins.remove(cryptoCoin);
        cryptoCoin.getPortfolios().remove(this);
    }
}
