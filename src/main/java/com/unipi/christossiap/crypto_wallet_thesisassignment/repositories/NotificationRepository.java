package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
