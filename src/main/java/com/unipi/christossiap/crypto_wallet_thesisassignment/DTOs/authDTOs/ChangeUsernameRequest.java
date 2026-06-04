package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.authDTOs;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators.UsernameNotExistsConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ChangeUsernameRequest(
        @NotNull(message = "Username cannot be null")
        @Pattern(regexp = "^[0-9a-zA-Z]{5,20}$|^$", message = "Invalid username (5-20 Latin characters and/or numbers)")
        @UsernameNotExistsConstraint
        String username
) {}
