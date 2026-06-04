package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.portfolioDTOs;


import java.util.List;

public record UserPortfolioResponse(
        String username,
        Double balance,
        List<PortfolioCoinItem> coins,
        Double evaluation
) {}
