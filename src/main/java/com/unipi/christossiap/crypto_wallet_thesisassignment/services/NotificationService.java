package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
}
