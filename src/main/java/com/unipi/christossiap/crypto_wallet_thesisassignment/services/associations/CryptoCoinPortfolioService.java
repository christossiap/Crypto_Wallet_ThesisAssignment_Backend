package com.unipi.christossiap.crypto_wallet_thesisassignment.services.associations;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.CryptoCoinPortfolioBreakdown;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.associations.CryptoCoinPortfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.associations.CryptoCoinPortfolioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoCoinPortfolioService {
    @Autowired
    private CryptoCoinPortfolioRepository cryptoCoinPortfolioRepository;


//    public List<CryptoCoinPortfolioBreakdown> getPortfolioBreakdown(Portfolio portfolio) {
//        return cryptoCoinPortfolioRepository.findByPortfolio(portfolio).stream()
//                .map(c -> new CryptoCoinPortfolioBreakdown(
//                        c.getCryptoCoin().getName(),
//                        c.getCoinAmount(),
//                        c.getCoinAmount() * c.getCryptoCoin().getPrice()
//                ))
//                .toList();
//    }
    //needs checking

    @Transactional
    public CryptoCoinPortfolio findOrCreate(Portfolio portfolio, CryptoCoin coin) {
        // Try to find an existing entry
        CryptoCoinPortfolio cryptoCoinPortfolio = cryptoCoinPortfolioRepository.findByPortfolioAndCryptoCoin(portfolio, coin);

        if (cryptoCoinPortfolio == null) {
            // Create and save a new entry if not found
            cryptoCoinPortfolio = new CryptoCoinPortfolio();
            cryptoCoinPortfolio.setPortfolio(portfolio);
            cryptoCoinPortfolio.setCryptoCoin(coin);
            cryptoCoinPortfolio.setCoinAmount(0.0); // Default amount
            cryptoCoinPortfolio = cryptoCoinPortfolioRepository.save(cryptoCoinPortfolio);
        }

        return cryptoCoinPortfolio;
    }

    @Transactional
    public Double getCoinAmountOfCryptoCoin(Portfolio portfolio, CryptoCoin coin){
        CryptoCoinPortfolio cryptoCoinPortfolio = findOrCreate(portfolio, coin);
        return cryptoCoinPortfolio.getCoinAmount();
    }

    @Transactional
    public void updateAmountOfCryptoCoin(Portfolio portfolio, Double amount, CryptoCoin coin) {
        // Get or create the CryptoCoinPortfolio entry
        CryptoCoinPortfolio cryptoCoinPortfolio = findOrCreate(portfolio, coin);

        // Update the coin amount
        cryptoCoinPortfolio.setCoinAmount(
                (cryptoCoinPortfolio.getCoinAmount() == null ? 0.0 : cryptoCoinPortfolio.getCoinAmount()) + amount
        );

        // Save the updated entry
        cryptoCoinPortfolioRepository.save(cryptoCoinPortfolio);
    }

    public List<CryptoCoinPortfolio> getAllCoinsInPortfolio(Portfolio portfolio) {
        return cryptoCoinPortfolioRepository.findByPortfolio(portfolio);
    }

    @Transactional
    public boolean canSellCryptoCoin(Portfolio portfolio, Double amount, CryptoCoin coin) {
        Double currentAmount = getCoinAmountOfCryptoCoin(portfolio, coin);
        return currentAmount != null && currentAmount >= amount;
    }


}
