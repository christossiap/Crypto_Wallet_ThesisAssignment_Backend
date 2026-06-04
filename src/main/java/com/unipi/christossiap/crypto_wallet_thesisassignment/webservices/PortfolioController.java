package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.portfolioDTOs.AddBalanceRequest;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.portfolioDTOs.UserPortfolioResponse;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.PortfolioService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/portfolio",produces = "application/json")
@Validated
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @GetMapping()
    public ResponseEntity<UserPortfolioResponse> handleRequest() {
        return ResponseEntity.ok(portfolioService.getUserPortfolio());
    }

    @GetMapping("/assets")
    public ResponseEntity<?> handleRequest2() {
        UserPortfolioResponse userPortfolioResponse = portfolioService.getUserPortfolio();
        return ResponseEntity.ok(userPortfolioResponse.coins());
    }


    @PostMapping("/add-balance")
    public ResponseEntity<String> handleRequest3(@Valid @RequestBody AddBalanceRequest request){
        portfolioService.addBalance(request.balance());
        return ResponseEntity.ok("Balance successfully added.");
    }

}
