package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPortfolioInfo {
    private String fullName;
    private Double balance;
    private String coinName;
    private Double coinAmount;
}
