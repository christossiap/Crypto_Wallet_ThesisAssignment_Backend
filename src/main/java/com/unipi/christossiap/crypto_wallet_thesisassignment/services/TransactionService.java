package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionSummary;
import com.unipi.christossiap.crypto_wallet_thesisassignment.enums.NotificationType;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Transaction;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.TransactionRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.associations.CryptoCoinPortfolioService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.InsufficientBalanceException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.InsufficientCoinsException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private NotificationService notificationService;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public void saveTransaction(Transaction transaction){transactionRepository.save(transaction);}
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
    public List<TransactionSummary> getUserTransactionsPagedAndSorted(Integer page, Integer size, String sortBy, String sortOrder) throws ResourceNotFoundException {
        User user = authService.getUser();
        Sort sort = (sortBy != null && sortOrder != null)
                ? Sort.by(Sort.Direction.fromString(sortOrder), sortBy)
                : Sort.unsorted();

        if (page != null && size != null) {
            PageRequest pageable = PageRequest.of(page, size, sort);
            Page<TransactionSummary> result = transactionRepository.findTransactionSummariesByPortfolioId(pageable,user.getPortfolio().getId());
            return result.hasContent() ? result.getContent() : new ArrayList<>();
        }
        return transactionRepository.findAllByPortfolioId(sort, user.getPortfolio().getId());
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

//    public TransactionSummary getTransactionSummary() throws ResourceNotFoundException {
//        User user = authService.getUser();
//        List<Transaction> transactions = transactionRepository.findAllByPortfolioId(user.getPortfolio().getId());
//        double totalBuy = transactions.stream()
//                .filter(t -> t.getTransactionType().equals("BUY"))
//                .mapToDouble(Transaction::getAmount).sum();
//        double totalSell = transactions.stream()
//                .filter(t -> t.getTransactionType().equals("SELL"))
//                .mapToDouble(Transaction::getAmount).sum();
//        return new TransactionSummary(totalBuy, totalSell, totalBuy - totalSell);
//    }

    @Transactional
    public void processTransaction(String cryptoCoinName, Double amount, String transactionType) throws Exception {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        CryptoCoin coin = cryptoCoinService.getCryptoCoinByName(cryptoCoinName);
        User user = authService.getUser();
        Portfolio portfolio = user.getPortfolio();

        Double balance = portfolio.getBalance();
        Double totalPrice = coin.getPrice() * amount;

        if (transactionType.equals("BUY")) {
            if (balance < totalPrice) {
                throw new InsufficientBalanceException("Insufficient balance to complete the purchase");
            }
            portfolioService.setBalance(portfolio, balance - totalPrice);
            portfolio.addCryptoCoin(coin);
            cryptoCoinPortfolioService.updateAmountOfCryptoCoin(portfolio,amount,coin);
            portfolioService.savePortfolio(portfolio);
            recordTransaction(portfolio,coin,amount,transactionType);
            try {
                notificationService.createNotification(
                        "Buy-Transaction",
                        "Your transaction has been successfully processed. \n" +
                                "Coin bought: "+ coin.getName() + "\n" +
                                "Amount: " + amount,
                        NotificationType.TRANSACTION
                );
            } catch (Exception e) {
                throw new IllegalArgumentException("Error creating notification..!");
            }
        } else if (transactionType.equals("SELL")) {
            if (cryptoCoinPortfolioService.getCoinAmountOfCryptoCoin(portfolio,coin) < amount) {
                throw new InsufficientCoinsException("Insufficient coins to sell");
            }
            portfolioService.setBalance(portfolio, balance + totalPrice);
            cryptoCoinPortfolioService.updateAmountOfCryptoCoin(portfolio,-amount,coin);
            if (cryptoCoinPortfolioService.getCoinAmountOfCryptoCoin(portfolio,coin)==0.0){
                portfolio.removeCryptoCoin(coin);
            }
            portfolioService.savePortfolio(portfolio);
            recordTransaction(portfolio,coin,amount,transactionType);
            try {
                notificationService.createNotification(
                        "Sell-Transaction",
                        "Your transaction has been successfully processed. \n" +
                                "Coin sold:"+ coin.getName() + "\n" +
                                "Amount:" + amount,
                        NotificationType.TRANSACTION
                );
            } catch (Exception e) {
                throw new IllegalArgumentException("Error creating notification..!");
            }
        } else {
            throw new IllegalArgumentException("Unknown transaction type");
        }
    }

    @Transactional
    public void deleteTransaction(Integer transactionId) throws ResourceNotFoundException {
        if (!transactionRepository.existsById(transactionId)) {
            throw new ResourceNotFoundException("Transaction not found with id: " + transactionId);
        }
        transactionRepository.deleteById(transactionId);
    }

}


