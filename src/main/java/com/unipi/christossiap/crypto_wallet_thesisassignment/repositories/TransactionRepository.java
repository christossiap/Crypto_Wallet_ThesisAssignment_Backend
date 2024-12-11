package com.unipi.christossiap.crypto_wallet_thesisassignment.repositories;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    List<Transaction> findAllByTransactionDateAfterAndTransactionDateBefore(LocalDateTime localDateTime1, LocalDateTime localDateTime2);

    List<Transaction> findByTransactionTypeAndPortfolio_UserId(String transactionType, Integer id);

    List<Transaction> findAllByPortfolioId(Integer id);

    List<Transaction> findAllByPortfolioId(Sort sort, Integer id);

    Page<Transaction> findAllByPortfolioId(PageRequest pageRequest, Integer id);

}
