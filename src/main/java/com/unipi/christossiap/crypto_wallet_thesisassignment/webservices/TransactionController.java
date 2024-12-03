package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.TransactionRequest;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Transaction;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.TransactionService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api",produces = "application/json")
@Validated
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/all-transactions")
    public List<Transaction> getAllTransactions() throws ResourceNotFoundException {
        return transactionService.getAllTransactions();
    }
    @GetMapping(value = "/user-transactions")
    public ResponseEntity<List<Transaction>> handeRequest1(){
        return ResponseEntity.ok(transactionService.getUserTransactions());
    }
    @GetMapping("/transactions-by-date")
    public List<Transaction> getUserTransactionsByDate(
            @RequestParam String startDate,
            @RequestParam String endDate) throws ResourceNotFoundException {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return transactionService.getTransactionsByDate(start, end);
    }

    @GetMapping("/transactions-by-type")
    public List<Transaction> getUserTransactionsByType(@RequestParam String transactionType) throws ResourceNotFoundException {
        return transactionService.getTransactionsByType(transactionType);
    }

    @PostMapping("/process")
    public String processUserTransaction(@RequestBody TransactionRequest transactionRequest) {
        try {
            transactionService.processTransaction(
                    transactionRequest.getCryptoCoinName(),
                    transactionRequest.getAmount(),
                    transactionRequest.getTransactionType());
            return "Transaction processed successfully!";
        } catch (Exception e) {
            return "Error processing transaction: " + e.getMessage();
        }
    }

    @DeleteMapping("/{transactionId}")
    public String deleteTransaction(@PathVariable Integer transactionId) {
        try {
            transactionService.deleteTransaction(transactionId);
            return "Transaction deleted successfully!";
        } catch (ResourceNotFoundException e) {
            return e.getMessage();
        }
    }


//    @Transactional
//    @PostMapping(value = "/transaction",consumes = "application/json")
//    public ResponseEntity<String> handleRequest2( @RequestBody Map<String, Object> map) throws Exception {
//
//        String cryptoCoinName = (String) map.get("cryptoCoinName");
//        Double amount = (Double) map.get("amount");
//        Integer portfolioId = ((Number) map.get("portfolioId")).intValue();
//        String transactionType = (String) map.get("transactionType");
//        transactionService.processTransaction(cryptoCoinName,amount,portfolioId,transactionType);
//        return ResponseEntity.ok("Done");
//    }
}
