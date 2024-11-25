package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
    private String username;
    private String password;
    private String email;
    private String role;
}
