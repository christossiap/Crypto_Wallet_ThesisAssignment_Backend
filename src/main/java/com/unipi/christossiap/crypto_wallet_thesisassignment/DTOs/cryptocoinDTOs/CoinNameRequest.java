package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.cryptocoinDTOs;

import jakarta.validation.constraints.NotBlank;

public record CoinNameRequest(@NotBlank String name) {}

