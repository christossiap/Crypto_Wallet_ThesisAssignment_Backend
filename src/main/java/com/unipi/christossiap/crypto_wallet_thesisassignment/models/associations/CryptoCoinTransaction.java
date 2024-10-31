package com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Transaction;
import jakarta.persistence.*;

@Entity
public class CryptoCoinTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cryptocoin_id")
    private CryptoCoin cryptoCoin;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
