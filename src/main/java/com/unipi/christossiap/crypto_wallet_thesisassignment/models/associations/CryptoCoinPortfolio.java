package com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCoinPortfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double coinAmount;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cryptocoin_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CryptoCoin cryptoCoin;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "portfolio_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Portfolio portfolio;


}
