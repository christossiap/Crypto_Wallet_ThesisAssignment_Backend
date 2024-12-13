package com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Transaction;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class CryptoCoinTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cryptocoin_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CryptoCoin cryptoCoin;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "transaction_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Transaction transaction;
}
