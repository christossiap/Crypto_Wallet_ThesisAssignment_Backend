package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Integer> {
    WatchList getWatchListByUser(User user);
}
