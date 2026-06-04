package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.NotificationDTOs.NotificationResponse;
import com.unipi.christossiap.crypto_wallet_thesisassignment.enums.NotificationType;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Notification;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.NotificationRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.AuthException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AuthService authService;

    public List<NotificationResponse> getAllNotifications() {
        User user = authService.getUser();

        return notificationRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<NotificationResponse> getUnreadNotifications() {
        User user = authService.getUser();

        return notificationRepository
                .findByUserIdAndIsRead(user.getId(), false)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public void createNotification(String title, String message, NotificationType type) {
        User user = authService.getUser();
        try {
            Notification notification = new Notification();
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(type);
            notification.setSentAt(LocalDateTime.now());
            notification.setUser(user);
            notificationRepository.save(notification);
        } catch(Exception e){
            throw new RuntimeException("Error creating notification!",e);
        }
    }


    @Transactional
    public void markNotificationAsRead(Integer notificationId) throws ResourceNotFoundException {
        User user = authService.getUser();
        Notification notification = notificationRepository.findNotificationById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        notification.setIsRead(Boolean.TRUE);
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new AuthException("Access denied");
        }
    }

    @Transactional
    public void deleteNotification(Integer notificationId) throws ResourceNotFoundException {
        Notification notification = notificationRepository.findNotificationById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found!"));
        notificationRepository.delete(notification);
    }

    private NotificationResponse mapToResponse(Notification notification) {

        return new NotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getNotificationType(),
                notification.getIsRead(),
                notification.getSentAt()
        );
    }

}
