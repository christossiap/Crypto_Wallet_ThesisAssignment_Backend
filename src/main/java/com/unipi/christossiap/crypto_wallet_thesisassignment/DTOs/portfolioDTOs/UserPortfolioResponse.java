package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.portfolioDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPortfolioResponse {
    private String username;
    private Double balance;
    private List<Map<String, Object>> coins;
    private Double evaluation;
}
