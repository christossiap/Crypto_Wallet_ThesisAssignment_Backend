package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.profileDTOs;

public record UserProfileInfo(
         String username,
         String email,
         String firstName,
         String lastName,
         String phoneNumber,
         String country,
         String city,
         String addressLine,
         String postalCode,
         String bio
) {
}
