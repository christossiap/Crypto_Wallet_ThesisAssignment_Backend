package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators.UsernameNotExistsConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @UsernameNotExistsConstraint
    private String username;
}
