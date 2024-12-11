package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.portfolioDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPortfolioInfo {
    private String username;
    private Double balance;
    private String coinName;
    private Double coinAmount;
}
