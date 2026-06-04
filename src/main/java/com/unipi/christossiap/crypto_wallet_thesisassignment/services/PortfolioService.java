package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.portfolioDTOs.PortfolioCoinItem;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.portfolioDTOs.UserPortfolioResponse;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.PortfolioItem;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.PortfolioRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.AuthException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.BalanceDepositionException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private AuthService authService;
    public void savePortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    public UserPortfolioResponse getUserPortfolio() {
        User user = authService.getUser();
        Portfolio portfolio = portfolioRepository.findByUserIdWithItems(user.getId())
                .orElseThrow(() -> new RuntimeException("No assets has been found."));
        List<PortfolioItem> assets = portfolio.getPortfolioItems();

        List<PortfolioCoinItem> coins = assets.stream()
                .map(item -> new PortfolioCoinItem(
                item.getCryptoCoin().getName(),
                item.getCoinAmount()
                ))
                .toList();

        Double evaluation = assets.stream()
                .mapToDouble(item -> item.getCryptoCoin().getPrice() * item.getCoinAmount())
                .sum();

        return new UserPortfolioResponse(
        user.getUsername(),
        user.getPortfolio().getBalance(),
        coins,
        evaluation);
    }

    @Transactional
    public void addBalance(Double balance){
        User user = authService.getUser();
        Portfolio portfolio = portfolioRepository.findPortfolioByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("No assets has been found."));
        portfolio.setBalance(portfolio.getBalance() + balance);
        savePortfolio(portfolio);
    }
    @Transactional
    public void setBalance(Portfolio portfolio, Double newBalance){
        portfolio.setBalance(newBalance);
        savePortfolio(portfolio);
    }

}

