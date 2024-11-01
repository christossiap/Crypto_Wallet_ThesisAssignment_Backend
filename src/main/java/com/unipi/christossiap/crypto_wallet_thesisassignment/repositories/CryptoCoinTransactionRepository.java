package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations.CryptoCoinTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCoinTransactionRepository extends JpaRepository<CryptoCoinTransaction, Integer> {
}
