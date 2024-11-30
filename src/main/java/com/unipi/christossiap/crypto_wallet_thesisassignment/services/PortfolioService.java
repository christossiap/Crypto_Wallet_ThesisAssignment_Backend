package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserPortfolioInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.PortfolioRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.Port;
import java.util.List;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);

    public void savePortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }
    public Boolean isPortfolioNull(Portfolio portfolio){
        return portfolio == null;
    }
    public void addNewPortfolioIfNecessary(User user, Portfolio portfolio){
        if (isPortfolioNull(portfolio)){
            Portfolio newPortfolio = new Portfolio();
            user.addPortfolio(newPortfolio);
            authService.saveUser(user);
        }
    }
    public Portfolio getPortfolioByUserId(Integer id) throws ResourceNotFoundException {
        User user = authService.getUser();
        addNewPortfolioIfNecessary(user,portfolioRepository.findPortfolioByUserId(id));
        return portfolioRepository.findPortfolioByUserId(user.getId());
    }


    public List<UserPortfolioInfo> getUserPortfolio() {
        try {
            User user = authService.getUser();
            return portfolioRepository.findUserPortfolioInfo(user.getId());
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void addBalance(Double balance) throws ResourceNotFoundException {
        User user = authService.getUser();
        Portfolio portfolio = getPortfolioByUserId(user.getId());
        portfolio.setBalance(portfolio.getBalance() + balance);
        savePortfolio(portfolio);
    }

}

