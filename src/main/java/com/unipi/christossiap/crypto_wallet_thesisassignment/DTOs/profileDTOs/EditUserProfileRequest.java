package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.profileDTOs;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators.UsernameNotExistsConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EditUserProfileRequest(
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

        @Pattern(regexp = "^\\+30[0-9]{7,15}$", message = "Phone number must be valid and contain 7 to 15 digits, optionally starting with '+30'")
        String phoneNumber,

        @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters")
        String country,
        @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
        String city,

        @Size(max = 100, message = "Address line must be at most 100 characters")
        String addressLine,

        @Pattern(regexp = "^[0-9]{4,10}$", message = "Postal code must be 4 to 10 digits")
        String postalCode,

        @Size(max = 250, message = "Bio must be at most 250 characters")
        String bio
) {}
