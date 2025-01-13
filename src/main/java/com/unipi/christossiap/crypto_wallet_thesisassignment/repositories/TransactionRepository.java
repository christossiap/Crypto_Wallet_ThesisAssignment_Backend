package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionSummary;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    List<Transaction> findAllByTransactionDateAfterAndTransactionDateBefore(LocalDateTime localDateTime1, LocalDateTime localDateTime2);

    List<Transaction> findByTransactionTypeAndPortfolio_UserId(String transactionType, Integer id);

    List<Transaction> findAllByPortfolioId(Integer id);

    List<Transaction> findTransactionsByPortfolioId(Sort sort, Integer id);

//    Page<Transaction> findAllByPortfolioId(PageRequest pageRequest, Integer id);

    @Query("SELECT new com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionSummary(" +
            "t.transactionDate, t.transactionType, c.name , t.amount, t.priceAtTransaction, " +
            "CAST(t.amount * t.priceAtTransaction AS double)) " +
            "FROM Transaction t " +
            "JOIN CryptoCoinTransaction ct on ct.transaction.id = t.id" +
            "   join  CryptoCoin c on ct.cryptoCoin.id = c.id" +
            "   WHERE t.portfolio.id = :portfolioId")
    Page<TransactionSummary> findTransactionSummariesByPortfolioId(PageRequest pageRequest, Integer portfolioId);

    @Query("SELECT new com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionSummary(" +
            "t.transactionDate, t.transactionType, c.name, t.amount, t.priceAtTransaction, " +
            "CAST(t.amount * t.priceAtTransaction AS double)) " +
            "FROM Transaction t " +
            "JOIN t.cryptoCoins c " +
            "WHERE t.portfolio.id = :portfolioId")
    List<TransactionSummary> findAllByPortfolioId(Sort sort, Integer portfolioId);



}
