package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.associations;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations.CryptoCoinWatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCoinWatchListRepository extends JpaRepository<CryptoCoinWatchList, Integer> {
}
