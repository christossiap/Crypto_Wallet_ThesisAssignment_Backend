package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import jakarta.persistence.*;
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
    private String preferredCurrency; // e.g., USD, EUR
    private String notificationPreferences; // How users want to receive notifications

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
