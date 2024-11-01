package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Transaction;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.PortfolioRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.TransactionRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CryptoCoinService cryptoCoinService;
    @Autowired
    private PortfolioService portfolioService;

    public void processTransaction(String cryptoCoinName, Double amount, Integer portfolioId, String transactionType) throws Exception {
        CryptoCoin coin = cryptoCoinService.getCryptoCoin(cryptoCoinName);
        Portfolio portfolio = portfolioService.getPortfolio(portfolioId); // Assuming a getPortfolio method exists in PortfolioService

        Double balance = portfolio.getBalance();
        Double totalPrice = coin.getPrice() * amount;

        switch (transactionType) {
            case "BUY":
                if (balance < totalPrice) {
                    throw new Exception("Insufficient balance to complete the purchase");
                }
                portfolioService.setPortfolioBalance(portfolioId, balance - totalPrice);
                portfolio.setCoinAmount(portfolio.getCoinAmount() + amount);
                break;

            case "SELL":
                if (portfolio.getCoinAmount() < amount) {
                    throw new Exception("Insufficient coins to sell");
                }
                portfolioService.setPortfolioBalance(portfolioId, balance + totalPrice);
                portfolio.setCoinAmount(portfolio.getCoinAmount() - amount);
                break;

            default:
                throw new IllegalArgumentException("Unknown transaction type");
        }

        // Create and save transaction record
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setPriceAtTransaction(coin.getPrice());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(transactionType);
        transaction.setPortfolio(portfolio);

        transactionRepository.save(transaction);
        portfolioService.savePortfolio(portfolio);  // Save updated portfolio via PortfolioService
    }
}

