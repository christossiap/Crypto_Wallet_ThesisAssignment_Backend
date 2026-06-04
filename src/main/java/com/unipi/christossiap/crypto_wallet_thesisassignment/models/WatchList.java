package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WatchList {
    @Id
    private Integer id;

    @OneToOne
    @MapsId
    @JsonBackReference
    @JoinColumn(name = "user_id", unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToMany(mappedBy = "watchLists")
    private List<CryptoCoin> cryptoCoins = new ArrayList<>();


    public void addCryptoCoin(CryptoCoin cryptoCoin){
        if (!cryptoCoins.contains(cryptoCoin)) {
            cryptoCoins.add(cryptoCoin);

        }
        if (!cryptoCoin.getWatchLists().contains(this)) {
            cryptoCoin.getWatchLists().add(this);
        }
    }
    public void removeCryptoCoin(CryptoCoin cryptoCoin) {
        cryptoCoins.remove(cryptoCoin);
        cryptoCoin.getWatchLists().remove(this);
    }

}
