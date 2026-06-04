package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.authDTOs;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators.EmailNotExistsConstraint;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators.UsernameNotExistsConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @NotNull(message = "Username cannot be null")
        @Pattern(regexp = "^[0-9a-zA-Z]{5,20}$|^$", message = "Invalid username (5-20 Latin characters and/or numbers)")
        @UsernameNotExistsConstraint
        String username,

        @NotNull(message = "Password cannot be null")
        @Pattern(regexp = "^[0-9a-zA-Z]{1,20}$", message = "Invalid password (5-20 Latin characters and/or numbers)")
        String password,

        @NotNull(message="The email must be provided")
        @Email
        @EmailNotExistsConstraint
        String email
) {}
