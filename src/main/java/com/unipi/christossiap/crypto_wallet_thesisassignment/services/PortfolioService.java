package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;

    public Double getPortfolioBalance(Integer portfolioId){
        Portfolio portfolio = portfolioRepository.findPortfolioById(portfolioId);
        return portfolio.getBalance();
    }

    public void setPortfolioBalance(Integer portfolioId,Double newBalance){
        Portfolio portfolio = portfolioRepository.findPortfolioById(portfolioId);
        portfolio.setBalance(newBalance);
        portfolioRepository.save(portfolio);
    }

}
