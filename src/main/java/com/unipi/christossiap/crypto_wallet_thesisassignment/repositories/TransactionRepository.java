package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionSummary;
import com.unipi.christossiap.crypto_wallet_thesisassignment.enums.TransactionType;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    @Query("""
    SELECT new com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionSummary(
        t.id,
        t.amount,
        t.priceAtTransaction,
        t.transactionDate,
        c.name,
        t.transactionType,
        CAST(t.amount * t.priceAtTransaction AS double)
    )
    FROM Transaction t
    JOIN t.cryptoCoin c
    WHERE t.portfolio.user.id = :userId
    AND t.transactionType = :transactionType
""") List<TransactionSummary> findTransactionSummariesByType(Integer userId, TransactionType transactionType);
    @Query("""
        SELECT new com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionSummary(
        t.id,
        t.amount,
        t.priceAtTransaction,
        t.transactionDate,
        c.name,
        t.transactionType,
        CAST(t.amount * t.priceAtTransaction AS double)
    )
    FROM Transaction t
    JOIN t.cryptoCoin c
    WHERE t.portfolio.user.id = :userId
        AND t.transactionDate BETWEEN :start AND :end
""")
    List<TransactionSummary> findTransactionSummariesByDate(Integer userId, LocalDateTime start, LocalDateTime end);
    @Query("""
        SELECT new com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionSummary(
        t.id,
        t.amount,
        t.priceAtTransaction,
        t.transactionDate,
        c.name,
        t.transactionType,
        CAST(t.amount * t.priceAtTransaction AS double)
    )
    FROM Transaction t
    JOIN t.cryptoCoin c
    WHERE t.portfolio.id = :portfolioId
""")
    Page<TransactionSummary> findTransactionSummariesByPortfolioId(PageRequest pageRequest, Integer portfolioId);

    @Query("""
            SELECT new com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionSummary(
        t.id,
        t.amount,
        t.priceAtTransaction,
        t.transactionDate,
        c.name,
        t.transactionType,
        CAST(t.amount * t.priceAtTransaction AS double)
    )
    FROM Transaction t
    JOIN t.cryptoCoin c
    WHERE t.portfolio.id = :portfolioId
""")
    List<TransactionSummary> findAllByPortfolioId(Sort sort, Integer portfolioId);

}
