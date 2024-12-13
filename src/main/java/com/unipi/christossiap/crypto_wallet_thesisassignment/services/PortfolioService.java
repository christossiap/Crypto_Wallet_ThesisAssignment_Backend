package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.portfolioDTOs.UserPortfolioInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.portfolioDTOs.UserPortfolioResponse;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.PortfolioRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private CryptoCoinService cryptoCoinService;
    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);

    public void savePortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }
    public Boolean isPortfolioNull(Portfolio portfolio){
        return portfolio == null;
    }

    @Transactional
    public void addNewPortfolioIfNecessary(User user, Portfolio portfolio){
        if (isPortfolioNull(portfolio)){
            Portfolio newPortfolio = new Portfolio();
            newPortfolio.setBalance(0.0);
            user.addPortfolio(newPortfolio);
            authService.saveUser(user);
        }
    }
    @Transactional
    public Portfolio getPortfolioByUserId() throws ResourceNotFoundException {
        User user = authService.getUser();
        addNewPortfolioIfNecessary(user,portfolioRepository.findPortfolioByUserId(user.getId()));
        return portfolioRepository.findPortfolioByUserId(user.getId());
    }


    public UserPortfolioResponse getUserPortfolio() throws ResourceNotFoundException {
        User user = authService.getUser();
        List<UserPortfolioInfo> list = portfolioRepository.findUserPortfolioInfo(user.getId());
        double evaluation = 0.0;
        List<Map<String, Object>> coinInfoList = new ArrayList<>();

        for (UserPortfolioInfo portfolioInfo : list) {
            Map<String, Object> coinInfo = new HashMap<>();
            coinInfo.put("coinName", portfolioInfo.getCoinName());
            coinInfo.put("coinAmount", portfolioInfo.getCoinAmount());
            coinInfoList.add(coinInfo);
            evaluation+= cryptoCoinService.getCryptoCoinByName(portfolioInfo.getCoinName()).getPrice()*portfolioInfo.getCoinAmount();
        }

        UserPortfolioResponse response = new UserPortfolioResponse();
        response.setUsername(user.getUsername());
        response.setBalance(user.getPortfolio().getBalance());
        response.setCoins(coinInfoList);
        response.setEvaluation(evaluation);
                
        return response;
    }



    @Transactional
    public void addBalance(Double balance) throws ResourceNotFoundException {
        if (balance == null || balance <= 0) {
            throw new RuntimeException("Λάθος ποσό! Παρακαλώ προσπαθήστε ξανά!");
        }
//        if (balance < 5 || balance > 10000) {
//            throw new RuntimeException("Η κατάθεση πρέπει να είναι από 5 έως 10000 ευρώ!");
//        }
        Portfolio portfolio = getPortfolioByUserId();
        portfolio.setBalance(portfolio.getBalance() + balance);
        savePortfolio(portfolio);
    }
    @Transactional
    public void setBalance(Portfolio portfolio, Double newBalance){
        portfolio.setBalance(newBalance);
        savePortfolio(portfolio);
    }

}

