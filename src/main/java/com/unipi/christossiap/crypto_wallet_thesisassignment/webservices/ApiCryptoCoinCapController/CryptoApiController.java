package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices.ApiCryptoCoinCapController;

import com.unipi.christossiap.crypto_wallet_thesisassignment.services.ApiCryptoCoinCapService.CryptoApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class CryptoApiController {
    @Autowired
    private CryptoApiService cryptoApiService;

    @GetMapping("/get-prices")
    public void handleRequest(){
        cryptoApiService.fetchAndSaveCryptoData();
    }
}
