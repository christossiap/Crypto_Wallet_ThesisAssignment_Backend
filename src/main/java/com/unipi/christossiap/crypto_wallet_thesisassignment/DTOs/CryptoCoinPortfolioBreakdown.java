package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCoinPortfolioBreakdown {
    private String coinName;
    private String symbol;
    private Double amountHeld;
    private Double currentPrice;
    private Double totalValue;

    public CryptoCoinPortfolioBreakdown(String name, Double coinAmount, double v) {
    }
}
