package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.authDTOs;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators.EmailNotExistsConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ChangeEmailRequest(
        @NotNull(message="The email must be provided")
        @Email
        @EmailNotExistsConstraint
        String email
) {}
