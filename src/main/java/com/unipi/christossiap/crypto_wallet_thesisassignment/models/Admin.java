package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin {
    @Id
    private Integer id;
    private String fullName;
    private String email;
    private String password;
}
