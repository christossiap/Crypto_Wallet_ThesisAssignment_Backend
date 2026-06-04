package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.NotificationDTOs;

import com.unipi.christossiap.crypto_wallet_thesisassignment.enums.NotificationType;

import java.time.LocalDateTime;

public record NotificationResponse(
        Integer id,
        String title,
        String message,
        NotificationType notificationType,
        boolean isRead,
        LocalDateTime sentAt
) {}