package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.authDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CodeRequest(
        @NotNull @NotBlank String code
) {}
