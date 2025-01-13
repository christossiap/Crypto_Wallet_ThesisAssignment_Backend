package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.enums.NotificationType;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Notification;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.NotificationRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
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

    public List<Notification> getAllNotifications(){
        User user = authService.getUser();
        return notificationRepository.findByUserId(user.getId());
    }

    public List<Notification> getUnreadNotifications(){
        User user = authService.getUser();
        return notificationRepository.findByUserIdAndIsRead(user.getId(), false);
    }

    @Transactional
    public void createNotification(String title, String message, NotificationType type) throws ResourceNotFoundException {
        User user = authService.getUser();
        try {
            Notification notification = new Notification();
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setNotificationType(type);
            notification.setSentAt(LocalDateTime.now());
            notification.setIsRead(false);
            notificationRepository.save(notification);
            user.addNotification(notification);
        } catch(Exception e){
            throw new ResourceNotFoundException("Λάθος Notification, προσπαθήστε ξανά!");
        }
    }


    public void markNotificationAsRead(Integer notificationId) throws ResourceNotFoundException {
        Notification notification = notificationRepository.findNotificationById(notificationId);
        if (notification == null ){throw new ResourceNotFoundException("Δεν βρέθηκε ειδοποίηση!");}
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(Integer notificationId) throws ResourceNotFoundException {
        Notification notification = notificationRepository.findNotificationById(notificationId);
        if (notification == null ){throw new ResourceNotFoundException("Δεν βρέθηκε ειδοποίηση!");}
        notificationRepository.delete(notification);
    }

}
