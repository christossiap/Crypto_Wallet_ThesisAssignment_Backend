package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionRequest;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionSummary;
import com.unipi.christossiap.crypto_wallet_thesisassignment.enums.TransactionType;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.TransactionService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.InsufficientBalanceException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.InsufficientCoinsException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api/transactions",produces = "application/json")
@Validated
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @GetMapping
    public List<TransactionSummary> handleRequest1(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "ASC") String sortOrder) throws ResourceNotFoundException {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page must be >= 0 and size must be > 0.");
        }
        return transactionService.getUserTransactionsPagedAndSorted(page, size, sortBy, sortOrder);
    }

    @GetMapping("/transactions-by-date")
    public List<TransactionSummary> handleRequest2(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String endDate){
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return transactionService.getTransactionsByDate(start, end);
    }

    @GetMapping("/transactions-by-type")
    public List<TransactionSummary> handleRequest3(@RequestParam TransactionType transactionType) throws ResourceNotFoundException {
        return transactionService.getTransactionsByType(transactionType);
    }

    @PostMapping("/process-transaction")
    public ResponseEntity<?> processTransaction(@Valid @RequestBody TransactionRequest request) {
        try {
            transactionService.processTransaction(request.cryptoCoinName(), request.amount(), request.transactionType());
            return ResponseEntity.ok("Transaction successful");
        } catch (InsufficientBalanceException | InsufficientCoinsException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing transaction: " + e.getMessage());
        }
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Integer transactionId) throws ResourceNotFoundException {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }

}
