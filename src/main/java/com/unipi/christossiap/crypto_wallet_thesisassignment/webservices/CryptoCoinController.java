package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.CryptoCoinService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.hibernate.annotations.CreationTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api",produces = "application/json")
@Validated
public class CryptoCoinController {

    //private static final Logger logger = LoggerFactory.getLogger(CryptoCoinController.class);
    @Autowired
    private CryptoCoinService cryptoCoinService;

    @GetMapping(value = "/crypto/{name}")
    public ResponseEntity<CryptoCoin> handleRequest1(@PathVariable @Valid String name) throws ResourceNotFoundException {
        CryptoCoin coin = cryptoCoinService.getCryptoCoin(name);
        return ResponseEntity.ok(coin);
    }

    @GetMapping(value = "/cryptos")
    public ResponseEntity<List<CryptoCoin>> handleRequest2() throws ResourceNotFoundException {
        return ResponseEntity.ok(cryptoCoinService.getAllCryptoCoins());
    }

    @GetMapping(value = "/test")
    public ResponseEntity<List<CryptoCoin>> handleRequest3(){
        List<CryptoCoin> cryptoCoins = cryptoCoinService.searchCryptoCoins(null,null,
                5.0,null);
        return ResponseEntity.ok(cryptoCoins);
    }
}
