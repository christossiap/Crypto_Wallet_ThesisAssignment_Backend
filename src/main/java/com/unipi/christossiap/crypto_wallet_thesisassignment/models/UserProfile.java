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
public class UserProfile {
    @Id
    private Integer id;
    private Integer userId; // Foreign key to User
    private String preferredCurrency; // e.g., USD, EUR
    private String notificationPreferences; // How users want to receive notifications
}
