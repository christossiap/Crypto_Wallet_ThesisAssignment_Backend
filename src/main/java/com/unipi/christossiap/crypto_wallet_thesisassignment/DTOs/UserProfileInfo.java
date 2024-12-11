package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileInfo {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String country;
    private String city;
    private String addressLine;
    private String postalCode;
    private String bio;
}
