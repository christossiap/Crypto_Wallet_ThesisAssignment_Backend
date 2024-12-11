package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.portfolioDTOs.UserPortfolioInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,Integer> {
    Portfolio findPortfolioByUserId(Integer userid);

    @Query("SELECT new com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.portfolioDTOs.UserPortfolioInfo(u.username, p.balance, c.name, cp.coinAmount) " +
            "FROM Portfolio p " +
            "JOIN p.user u " +
            "JOIN CryptoCoinPortfolio cp ON p.id = cp.portfolio.id " +
            "JOIN cp.cryptoCoin c" +
            "   where u.id=:user_id")
    List<UserPortfolioInfo> findUserPortfolioInfo(@Param("user_id") Integer user_id);
}
