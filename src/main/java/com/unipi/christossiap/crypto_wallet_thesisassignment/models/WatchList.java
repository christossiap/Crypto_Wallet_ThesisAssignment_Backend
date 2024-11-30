package com.unipi.christossiap.crypto_wallet_thesisassignment.models;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToMany(mappedBy = "watchLists")
    private List<CryptoCoin> cryptoCoins = new ArrayList<>();

    public void addCryptoCoin(CryptoCoin cryptoCoin){
        if (!cryptoCoins.contains(cryptoCoin)) {
            cryptoCoins.add(cryptoCoin);
        }
        // Ensure bidirectional consistency: add the WatchList to the CryptoCoin's watchLists
        if (!cryptoCoin.getWatchLists().contains(this)) {
            cryptoCoin.getWatchLists().add(this);
        }
    }
    public void removeCryptoCoin(CryptoCoin cryptoCoin) {
        cryptoCoins.remove(cryptoCoin);
        cryptoCoin.getWatchLists().remove(this);
    }

}
