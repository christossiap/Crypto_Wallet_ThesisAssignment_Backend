package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoinHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoCoinHistoryRepository extends JpaRepository<CryptoCoinHistory,Integer> {
    List<CryptoCoinHistory> findAllByCryptoCoinId(Integer id);
}
