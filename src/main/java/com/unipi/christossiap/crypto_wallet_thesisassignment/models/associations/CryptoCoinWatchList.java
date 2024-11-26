package com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import jakarta.persistence.*;

@Entity
public class CryptoCoinWatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cryptocoin_id")
    private CryptoCoin cryptoCoin;

    @ManyToOne
    @JoinColumn(name = "watchlist_id")
    private WatchList watchList;


}
