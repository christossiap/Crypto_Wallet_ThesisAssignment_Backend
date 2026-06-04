package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CryptoCoinRepository extends JpaRepository<CryptoCoin, Integer>,
                                              JpaSpecificationExecutor<CryptoCoin> {
    Optional<CryptoCoin> findCryptoCoinById(int id);
    Optional<CryptoCoin> findCryptoCoinByName(String name);

}
