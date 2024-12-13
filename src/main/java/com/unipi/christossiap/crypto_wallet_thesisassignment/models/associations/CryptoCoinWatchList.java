package com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class CryptoCoinWatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cryptocoin_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CryptoCoin cryptoCoin;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "watchlist_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private WatchList watchList;


}
