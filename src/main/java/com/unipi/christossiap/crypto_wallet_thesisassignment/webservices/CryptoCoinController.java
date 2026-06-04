package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.cryptocoinDTOs.CryptoCoinResponse;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.CryptoCoinService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping(path = "/api/coin/",produces = "application/json")
public class CryptoCoinController {

    @Autowired
    private CryptoCoinService cryptoCoinService;

    @GetMapping("/{name}")
    public ResponseEntity<CryptoCoinResponse> handleRequest1(@PathVariable  String name) throws ResourceNotFoundException {
        return ResponseEntity.ok(cryptoCoinService.getCryptoCoinResponseByName(name));
    }

    @GetMapping("/cryptos/{id}")
    public ResponseEntity<CryptoCoinResponse> handleRequest4(@PathVariable Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(cryptoCoinService.getCryptoCoinResponseById(id));
    }

    @GetMapping("/cryptos")
    public ResponseEntity<List<CryptoCoin>> getCryptoCoins(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder) {

        List<CryptoCoin> cryptoCoins = cryptoCoinService.getAllCryptoCoins(page, size, sortBy, sortOrder);

        return ResponseEntity.ok(cryptoCoins);
    }


    @GetMapping("/search-cryptos")
    public ResponseEntity<List<CryptoCoin>> searchCryptoCoins(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String symbol,
            @RequestParam(required = false) Double priceGreaterThan,
            @RequestParam(required = false) Double priceLessThan,
            @RequestParam(required = false) Double percentageChange24hGreaterThan,
            @RequestParam(required = false) Double percentageChange24hLessThan,
            @RequestParam(required = false) LocalDateTime dateAfter,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {

        List<CryptoCoin> results = cryptoCoinService.searchCryptoCoins(
                name, symbol,
                priceGreaterThan, priceLessThan,
                percentageChange24hGreaterThan, percentageChange24hLessThan,
                dateAfter, startDate, endDate);

        return ResponseEntity.ok(results);
    }

}
