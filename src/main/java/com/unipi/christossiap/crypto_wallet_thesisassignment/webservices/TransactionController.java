package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Transaction;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.TransactionService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api",produces = "application/json")
@Validated
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = "/transactions")
    public ResponseEntity<List<Transaction>> handeRequest1() throws ResourceNotFoundException {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @Transactional
    @PostMapping(value = "/transaction",consumes = "application/json")
    public ResponseEntity<String> handleRequest2( @RequestBody Map<String, Object> map) throws Exception {

        String cryptoCoinName = (String) map.get("cryptoCoinName");
        Double amount = (Double) map.get("amount");
        Integer portfolioId = ((Number) map.get("portfolioId")).intValue();
        String transactionType = (String) map.get("transactionType");
        transactionService.processTransaction(cryptoCoinName,amount,portfolioId,transactionType);
        return ResponseEntity.ok("Done");
    }
}
