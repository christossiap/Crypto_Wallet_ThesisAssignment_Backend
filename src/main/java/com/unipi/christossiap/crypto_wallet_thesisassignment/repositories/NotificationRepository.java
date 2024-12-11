package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Notification;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByUserId(Integer user_id);
    List<Notification> findByUserIdAndIsRead(Integer user_id, boolean b);

    Notification findNotificationById(Integer id);
}
