package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Transaction;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.PortfolioRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.TransactionRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CryptoCoinService cryptoCoinService;
    @Autowired
    private PortfolioService portfolioService;


    private void processTransaction(String cryptoCoinName, Double amount, Integer portfolioId, String transactionType) throws Exception {
        CryptoCoin coin = cryptoCoinService.getCryptoCoin(cryptoCoinName);
        Double balance = portfolioService.getPortfolioBalance(portfolioId);

        switch (transactionType) {
            case "BUY":
                Double price = coin.getPrice()*amount;
                if (balance<price) {
                    throw new Exception("Δεν φτάνει το balance");
                }
                else {
                    portfolioService.setPortfolioBalance(portfolioId,balance-price);
                }
                break;
            case "SELL":
                portfolioService.setPortfolioBalance(portfolioId,balance+coin.getPrice());
                break;
            default:
                throw new IllegalArgumentException("Unknown transaction type");
        }


    }

}
