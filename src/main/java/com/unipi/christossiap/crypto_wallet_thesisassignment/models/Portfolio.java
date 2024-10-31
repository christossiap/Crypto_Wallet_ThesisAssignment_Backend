package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Portfolio {
    @Id
    private Integer id;
    private Integer userId; // Foreign key to User
    private Integer coinId; // Foreign key to CryptoCoin
    private Double holdings; // Amount of a specific crypto the user holds
}
