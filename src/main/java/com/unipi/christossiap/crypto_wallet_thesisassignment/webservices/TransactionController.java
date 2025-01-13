package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionRequest;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.transactionDTOs.TransactionSummary;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Transaction;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.TransactionService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.InsufficientBalanceException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.InsufficientCoinsException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api",produces = "application/json")
@Validated
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @GetMapping("/user-transactions")
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
    public List<Transaction> handleRequest2(
            @RequestParam String startDate,
            @RequestParam String endDate) throws ResourceNotFoundException {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return transactionService.getTransactionsByDate(start, end);
    }

    @GetMapping("/transactions-by-type")
    public List<Transaction> handleRequest3(@RequestParam String transactionType) throws ResourceNotFoundException {
        return transactionService.getTransactionsByType(transactionType);
    }

    @PostMapping("/process-transaction")
    public ResponseEntity<?> processTransaction(@RequestBody TransactionRequest request) {
        try {
            transactionService.processTransaction(request.getCryptoCoinName(), request.getAmount(), request.getTransactionType());
            return ResponseEntity.ok("Transaction successful");
        } catch (InsufficientBalanceException | InsufficientCoinsException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing transaction: " + e.getMessage());
        }
    }

    @DeleteMapping("/{transactionId}")
    public String handleRequest5(@PathVariable Integer transactionId) {
        try {
            transactionService.deleteTransaction(transactionId);
            return "Transaction deleted successfully!";
        } catch (ResourceNotFoundException e) {
            return e.getMessage();
        }
    }

}
