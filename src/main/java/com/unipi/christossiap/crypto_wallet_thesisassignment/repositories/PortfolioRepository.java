package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserPortfolioInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,Integer> {
    Portfolio findPortfolioById(Integer id);

    @Query("SELECT new com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserPortfolioInfo(u.fullName, p.balance, c.name, p.coinAmount) " +
            "FROM Portfolio p " +
            "JOIN p.user u " +
            "JOIN CryptoCoinPortfolio cp ON p.id = cp.portfolio.id " +
            "JOIN cp.cryptoCoin c")
    List<UserPortfolioInfo> findUserPortfolioInfo();
}
