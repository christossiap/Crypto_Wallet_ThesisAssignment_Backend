package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs;

import com.unipi.christossiap.crypto_wallet_thesisassignment.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationInfo {
    String title;
    String message;
    NotificationType notificationType;
}
