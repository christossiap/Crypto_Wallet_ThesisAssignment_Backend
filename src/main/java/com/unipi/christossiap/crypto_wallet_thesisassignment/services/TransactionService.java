package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.TransactionSummary;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Transaction;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.TransactionRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.associations.CryptoCoinPortfolioService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CryptoCoinService cryptoCoinService;
    @Autowired
    private PortfolioService portfolioService;
    @Autowired
    private AuthService authService;
    @Autowired
    private CryptoCoinPortfolioService cryptoCoinPortfolioService;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public void saveTransaction(Transaction transaction){transactionRepository.save(transaction);}

    public List<Transaction> getUserTransactions(){
        try {
            User user = authService.getUser();
            return transactionRepository.findByPortfolio_UserId(user.getPortfolio().getId());
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Transaction> getTransactionsByDate(LocalDateTime localDateTime1,LocalDateTime localDateTime2) throws ResourceNotFoundException {
        User user = authService.getUser();
        List<Transaction> transactions = transactionRepository.findAllByTransactionDateAfterAndTransactionDateBefore(localDateTime1,localDateTime2);
        List<Transaction> t = new ArrayList<>();
        for (Transaction transaction : transactions){
            if (Objects.equals(transaction.getPortfolio().getUser().getId(), user.getPortfolio().getId())){
                t.add(transaction);
            }
        }
        return t;
    }
    public List<Transaction> getTransactionsByType(String transactionType) throws ResourceNotFoundException {
        User user = authService.getUser();
        return transactionRepository.findByTransactionTypeAndPortfolio_UserId(transactionType, user.getPortfolio().getId());
    }

    public List<Transaction> getAllTransactions() throws ResourceNotFoundException {
        List<Transaction> list = transactionRepository.findAll();
        if (list.isEmpty()){
            throw new ResourceNotFoundException("Δεν βρέθηκαν transactions");
        }
        return list;
    }
//    public TransactionSummary getTransactionSummary() throws ResourceNotFoundException {
//        User user = authService.getUser();
//        List<Transaction> transactions = transactionRepository.findByPortfolio_UserId(user.getId());
//        double totalBuy = transactions.stream()
//                .filter(t -> t.getTransactionType().equals("BUY"))
//                .mapToDouble(Transaction::getAmount).sum();
//        double totalSell = transactions.stream()
//                .filter(t -> t.getTransactionType().equals("SELL"))
//                .mapToDouble(Transaction::getAmount).sum();
//        return new TransactionSummary(totalBuy, totalSell, totalBuy - totalSell);
//    }
    //needs checking

    @Transactional
    public void deleteTransaction(Integer transactionId) throws ResourceNotFoundException {
        if (!transactionRepository.existsById(transactionId)) {
            throw new ResourceNotFoundException("Transaction not found with id: " + transactionId);
        }
        transactionRepository.deleteById(transactionId);
    }

    @Transactional
    public void processTransaction(String cryptoCoinName, Double amount, String transactionType) throws Exception {
        CryptoCoin coin = cryptoCoinService.getCryptoCoinByName(cryptoCoinName);
        User user = authService.getUser();
        Portfolio portfolio = user.getPortfolio();

        Double balance = portfolio.getBalance();
        Double totalPrice = coin.getPrice() * amount;

        if (transactionType.equals("BUY")) {
            if (balance < totalPrice) {
                throw new Exception("Insufficient balance to complete the purchase");
            }
            portfolioService.setBalance(portfolio, balance - totalPrice);
            portfolio.addCryptoCoin(coin);
            cryptoCoinPortfolioService.updateAmountOfCryptoCoin(portfolio,amount,coin);
            portfolioService.savePortfolio(portfolio);
            recordTransaction(portfolio,coin,amount,transactionType);
        } else if (transactionType.equals("SELL")) {
            if (cryptoCoinPortfolioService.getCoinAmountOfCryptoCoin(portfolio,coin) < amount) {
                throw new Exception("Insufficient coins to sell");
            }
            portfolioService.setBalance(portfolio, balance + totalPrice);
            cryptoCoinPortfolioService.updateAmountOfCryptoCoin(portfolio,-amount,coin);
            if (cryptoCoinPortfolioService.getCoinAmountOfCryptoCoin(portfolio,coin)==0.0){
                logger.info("yyyy");
                portfolio.removeCryptoCoin(coin);
            }
            portfolioService.savePortfolio(portfolio);
            recordTransaction(portfolio,coin,amount,transactionType);
        } else {
            throw new IllegalArgumentException("Unknown transaction type");
        }
    }
    @Transactional
    public void recordTransaction(Portfolio portfolio, CryptoCoin coin, Double amount, String transactionType) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setPriceAtTransaction(coin.getPrice());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(transactionType);
        transaction.setPortfolio(portfolio);
        transaction.addCryptoCoin(coin);
        saveTransaction(transaction);
    }
}


