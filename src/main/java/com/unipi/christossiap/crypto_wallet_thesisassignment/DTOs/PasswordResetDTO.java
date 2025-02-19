package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDTO {

    // Getters and Setters
    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Code cannot be null")
    private String code;

    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "^[0-9a-zA-Z]{5,20}$", message = "Invalid password (5-20 Latin characters and/or numbers)")
    private String password;

}