package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.PortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PortfolioItemRepository extends JpaRepository<PortfolioItem,Integer> {

    Optional<PortfolioItem> findByPortfolioIdAndCryptoCoinName(Integer portfolioId, String name);

}
