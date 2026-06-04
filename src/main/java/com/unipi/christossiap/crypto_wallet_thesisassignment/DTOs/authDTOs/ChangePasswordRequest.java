package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.authDTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequest(
        @NotNull(message = "Password cannot be null")
        @Pattern(regexp = "^[0-9a-zA-Z]{1,20}$", message = "Invalid password (5-20 Latin characters and/or numbers)")
        String password
) {
}
