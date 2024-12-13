package com.unipi.christossiap.crypto_wallet_thesisassignment.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations.CryptoCoinWatchList;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.WatchListService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
