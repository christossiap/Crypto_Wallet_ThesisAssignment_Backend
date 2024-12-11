package com.unipi.christossiap.crypto_wallet_thesisassignment.models;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations.CryptoCoinWatchList;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.WatchListService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class WatchList {
    private static final Logger logger = LoggerFactory.getLogger(WatchList.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToMany(mappedBy = "watchLists")
    private List<CryptoCoin> cryptoCoins = new ArrayList<>();


    public void addCryptoCoin(CryptoCoin cryptoCoin){
        if (!cryptoCoins.contains(cryptoCoin)) {
            logger.info(String.valueOf("trying to add to cryptocoins sto watchlist"));
            cryptoCoins.add(cryptoCoin);

        }
        if (!cryptoCoin.getWatchLists().contains(this)) {
            logger.info(String.valueOf("trying to add sto watchlist tou crypto coin"));
            cryptoCoin.getWatchLists().add(this);
        }
    }
    public void removeCryptoCoin(CryptoCoin cryptoCoin) {
        cryptoCoins.remove(cryptoCoin);
        cryptoCoin.getWatchLists().remove(this);
    }

}
