package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.cryptocoinDTOs;

import java.time.LocalDateTime;

public record CryptoCoinResponse(
        Integer id,
        String name,
        String symbol,
        Long totalSupply,
        Double price,
        Double percentChange24h,
        Double marketCap,
        LocalDateTime lastUpdated
) {}
