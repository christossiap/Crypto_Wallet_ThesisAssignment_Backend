package com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCoinPortfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double coinAmount;
    @ManyToOne
    @JoinColumn(name = "cryptocoin_id")
    private CryptoCoin cryptoCoin;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;


}
