package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserPortfolioInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.PortfolioService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api",produces = "application/json")
@Validated
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/user-portfolio")
    public ResponseEntity<List<UserPortfolioInfo>> handleRequest2(){
        return ResponseEntity.ok(portfolioService.getUserPortfolio());
    }

    @PostMapping("/add-balance")
    public ResponseEntity<String> handleRequest3(@RequestBody @Valid Map<String,Double> balance) throws ResourceNotFoundException {
        portfolioService.addBalance(balance.get("balance"));
        return ResponseEntity.ok("The balance has been added...!");
    }

}
