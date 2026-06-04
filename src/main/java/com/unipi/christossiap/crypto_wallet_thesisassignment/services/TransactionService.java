package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionSummary;
import com.unipi.christossiap.crypto_wallet_thesisassignment.enums.NotificationType;
import com.unipi.christossiap.crypto_wallet_thesisassignment.enums.TransactionType;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.PortfolioItem;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Transaction;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.PortfolioItemRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.TransactionRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.AuthException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.InsufficientBalanceException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.InsufficientCoinsException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private NotificationService notificationService;

    @Autowired
    private PortfolioItemRepository portfolioItemRepository;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public void recordTransaction(Portfolio portfolio, CryptoCoin coin, Double amount, TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setPriceAtTransaction(coin.getPrice());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(transactionType);
        transaction.setPortfolio(portfolio);
        transaction.setCryptoCoin(coin);
        transactionRepository.save(transaction);
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

    public List<TransactionSummary> getTransactionsByDate(LocalDateTime start, LocalDateTime end) {
        User user = authService.getUser();
        return transactionRepository.findTransactionSummariesByDate(user.getId(), start, end);
    }
    public List<TransactionSummary> getTransactionsByType(TransactionType transactionType) {
        User user = authService.getUser();
        return transactionRepository.findTransactionSummariesByType(user.getPortfolio().getId(), transactionType);
    }

    @Transactional
    public void processTransaction(String cryptoCoinName, Double amount, TransactionType transactionType) throws Exception {
        if (amount == null || amount <= 0.0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        CryptoCoin coin = cryptoCoinService.getCryptoCoinByName(cryptoCoinName);
        User user = authService.getUser();
        Portfolio portfolio = user.getPortfolio();

        Double balance = portfolio.getBalance();
        Double totalPrice = coin.getPrice() * amount;

        if (transactionType == TransactionType.BUY) {
            if (balance < totalPrice) {
                throw new InsufficientBalanceException("Insufficient balance to complete the purchase");
            }
            PortfolioItem portfolioItem =
                    portfolioItemRepository
                            .findByPortfolioIdAndCryptoCoinName(
                                    portfolio.getId(),
                                    cryptoCoinName
                            )
                            .orElse(null);

            if (portfolioItem != null) {
                portfolioItem.setCoinAmount(
                        portfolioItem.getCoinAmount() + amount
                );
            } else {
                portfolioItem = new PortfolioItem();
                portfolioItem.setCoinAmount(amount);
                portfolioItem.setCryptoCoin(coin);
                portfolioItem.setPortfolio(portfolio);
            }

            portfolioItemRepository.save(portfolioItem);

            portfolioService.setBalance(portfolio, balance - totalPrice);
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
                logger.error("Failed to create transaction notification for user", e);            }
        } else if (transactionType == TransactionType.SELL) {
            PortfolioItem portfolioItem = portfolioItemRepository.findByPortfolioIdAndCryptoCoinName(portfolio.getId(),cryptoCoinName)
                    .orElseThrow(()-> new RuntimeException("Not portfolio item found..!"));
            if (portfolioItem.getCoinAmount() < amount) {
                throw new InsufficientCoinsException("Insufficient coins to sell");
            }
            portfolioService.setBalance(portfolio, balance + totalPrice);
            portfolioItem.setCoinAmount(portfolioItem.getCoinAmount()-amount);
            portfolioItemRepository.save(portfolioItem);
            if (portfolioItem.getCoinAmount() <= 0.0000000000001){
                portfolioItemRepository.delete(portfolioItem);
            }
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
                logger.error("Failed to send transaction notification, continuing", e);
            }
        } else {
            throw new IllegalArgumentException("Unknown transaction type");
        }
    }

    public void deleteTransaction(Integer transactionId) throws ResourceNotFoundException {
        User user = authService.getUser();
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        if (!transaction.getPortfolio().getUser().getId().equals(user.getId())) {
            throw new AuthException("Access denied");
        }
        transactionRepository.delete(transaction);
    }

}


