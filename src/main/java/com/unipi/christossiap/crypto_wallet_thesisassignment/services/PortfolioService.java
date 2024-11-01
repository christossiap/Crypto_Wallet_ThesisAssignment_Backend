package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.PortfolioRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;

    public Portfolio getPortfolio(Integer portfolioId) throws ResourceNotFoundException {
        return portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found with id: " + portfolioId));
    }

    public void savePortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    public Double getPortfolioBalance(Integer portfolioId) throws ResourceNotFoundException {
        Portfolio portfolio = getPortfolio(portfolioId);
        return portfolio.getBalance();
    }

    public void setPortfolioBalance(Integer portfolioId, Double newBalance) throws ResourceNotFoundException {
        Portfolio portfolio = getPortfolio(portfolioId);
        portfolio.setBalance(newBalance);
        savePortfolio(portfolio); // Using savePortfolio method for consistency
    }
}

