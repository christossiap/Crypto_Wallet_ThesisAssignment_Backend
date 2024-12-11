package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.portfolioDTOs.UserPortfolioResponse;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.PortfolioService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api",produces = "application/json")
@Validated
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/user-portfolio")
    public ResponseEntity<UserPortfolioResponse> handleRequest2() throws ResourceNotFoundException {
        UserPortfolioResponse userPortfolio = portfolioService.getUserPortfolio();
        return ResponseEntity.ok(userPortfolio);
    }

    @GetMapping("/user-assets")
    public ResponseEntity<?> handleRequest4() throws ResourceNotFoundException {
        UserPortfolioResponse userPortfolioResponse = portfolioService.getUserPortfolio();
        return ResponseEntity.ok(userPortfolioResponse.getCoins());
    }


    @PostMapping("/add-balance")
    public ResponseEntity<String> handleRequest3(@RequestBody Map<String,Double> balance) throws ResourceNotFoundException {
        portfolioService.addBalance(balance.get("balance"));
        return ResponseEntity.ok("Tο ποσό προστέθηκε επιτυχώς...!");
    }

}
