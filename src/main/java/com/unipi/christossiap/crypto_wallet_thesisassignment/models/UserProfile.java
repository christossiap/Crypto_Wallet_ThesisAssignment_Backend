package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String preferredCurrency; // e.g., USD, EUR
    private String notificationPreferences; // How users want to receive notifications

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}
