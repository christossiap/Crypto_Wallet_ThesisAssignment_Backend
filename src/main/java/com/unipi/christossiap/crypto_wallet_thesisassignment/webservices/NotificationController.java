package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.NotificationInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Notification;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.NotificationService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> handleRequest1(){
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/unread-notifications")
    public ResponseEntity<List<Notification>> handleRequest2(){
        return ResponseEntity.ok(notificationService.getUnreadNotifications());
    }

//    @PostMapping("/create-notification")
//    public ResponseEntity<String> handleRequest3(@RequestBody NotificationInfo notificationInfo) throws ResourceNotFoundException {
//        notificationService.createNotification(notificationInfo.getTitle(),notificationInfo.getMessage(),notificationInfo.getNotificationType());
//        return ResponseEntity.ok("User notified");
//    }

    @PutMapping("/notifications/{id}/mark-read")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Integer id) throws ResourceNotFoundException {
        notificationService.markNotificationAsRead(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-notification")
    public ResponseEntity<?> handleRequest4(@RequestParam Integer id) throws ResourceNotFoundException {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
