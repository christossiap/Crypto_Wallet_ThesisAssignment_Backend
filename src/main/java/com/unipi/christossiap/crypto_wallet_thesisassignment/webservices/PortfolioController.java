package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserPortfolioInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.PortfolioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api",produces = "application/json")
@Validated
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @Transactional
    @GetMapping(value = "/portfolio")
    public ResponseEntity<List<UserPortfolioInfo>> handleRequest1(){
        return ResponseEntity.ok(portfolioService.getAllUserPortfolioInfo());
    }
}
