package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCoinRepository extends JpaRepository<CryptoCoin, Integer>, JpaSpecificationExecutor<CryptoCoin> {
    CryptoCoin findCryptoCoinById(int id);
    CryptoCoin findCryptoCoinByName(String name);
    CryptoCoin findCryptoCoinByNameAndId(String name, int id);
}
