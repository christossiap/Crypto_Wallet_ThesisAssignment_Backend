package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.NotificationDTOs.NotificationResponse;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Notification;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.NotificationService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/notifications", produces = "application/json")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> handleRequest1() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/unread-notifications")
    public ResponseEntity<List<NotificationResponse>> handleRequest2() {
        return ResponseEntity.ok(notificationService.getUnreadNotifications());
    }
    @PatchMapping("/{id}/mark-read")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Integer id) throws ResourceNotFoundException {
        notificationService.markNotificationAsRead(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> handleRequest4(@PathVariable Integer id) throws ResourceNotFoundException {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
