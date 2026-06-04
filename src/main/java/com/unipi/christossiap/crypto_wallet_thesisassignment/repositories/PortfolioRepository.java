package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,Integer> {
    Optional<Portfolio> findPortfolioByUserId(Integer userid);

    @Query("SELECT p FROM Portfolio p JOIN FETCH p.portfolioItems pi JOIN FETCH pi.cryptoCoin WHERE p.user.id = :userId")
    Optional<Portfolio> findByUserIdWithItems(@Param("userId") Integer userId);
}
