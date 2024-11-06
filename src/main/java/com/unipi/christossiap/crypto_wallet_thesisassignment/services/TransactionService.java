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
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CryptoCoinService cryptoCoinService;
    @Autowired
    private PortfolioService portfolioService;

    public Transaction getTransaction(Integer transactionId) throws ResourceNotFoundException {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transactionId));
    }

    public List<Transaction> getAllTransactions() throws ResourceNotFoundException {
        List<Transaction> list = transactionRepository.findAll();
        if (list.isEmpty()){
            throw new ResourceNotFoundException("Δεν βρέθηκαν transactions");
        }
        return list;
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void deleteTransaction(Integer transactionId) throws ResourceNotFoundException {
        if (!transactionRepository.existsById(transactionId)) {
            throw new ResourceNotFoundException("Transaction not found with id: " + transactionId);
        }
        transactionRepository.deleteById(transactionId);
    }

    public void processTransaction(String cryptoCoinName, Double amount, Integer portfolioId, String transactionType) throws Exception {
        CryptoCoin coin = cryptoCoinService.getCryptoCoin(cryptoCoinName);
        Portfolio portfolio = portfolioService.getPortfolio(portfolioId);

        Double balance = portfolio.getBalance();
        Double totalPrice = coin.getPrice() * amount;

        if (transactionType.equals("BUY")) {
            processBuyTransaction(portfolio, balance, totalPrice, amount);
        } else if (transactionType.equals("SELL")) {
            processSellTransaction(portfolio, balance, totalPrice, amount);
        } else {
            throw new IllegalArgumentException("Unknown transaction type");
        }

        recordTransaction(portfolio, coin, amount, transactionType);
    }

    private void processBuyTransaction(Portfolio portfolio, Double balance, Double totalPrice, Double amount) throws Exception {
        if (balance < totalPrice) {
            throw new Exception("Insufficient balance to complete the purchase");
        }
        portfolioService.setPortfolioBalance(portfolio.getId(), balance - totalPrice);
        portfolio.setCoinAmount(portfolio.getCoinAmount() + amount);
        portfolioService.savePortfolio(portfolio);
    }

    private void processSellTransaction(Portfolio portfolio, Double balance, Double totalPrice, Double amount) throws Exception {
        if (portfolio.getCoinAmount() < amount) {
            throw new Exception("Insufficient coins to sell");
        }
        portfolioService.setPortfolioBalance(portfolio.getId(), balance + totalPrice);
        portfolio.setCoinAmount(portfolio.getCoinAmount() - amount);
        portfolioService.savePortfolio(portfolio);
    }

    private void recordTransaction(Portfolio portfolio, CryptoCoin coin, Double amount, String transactionType) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setPriceAtTransaction(coin.getPrice());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(transactionType);
        transaction.setPortfolio(portfolio);

        saveTransaction(transaction);
    }
}


