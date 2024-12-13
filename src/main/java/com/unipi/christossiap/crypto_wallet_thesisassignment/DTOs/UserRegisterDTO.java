package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
        private String username;
        private String password;
        private String email;
}
