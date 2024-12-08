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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://192.168.122.30:3000/") // Allow requests from frontend running on http://192.168.122.30:3000/
@RestController
@RequestMapping(path = "/api/coin/",produces = "application/json")
@Validated
public class CryptoCoinController {

    private static final Logger logger = LoggerFactory.getLogger(CryptoCoinController.class);
    @Autowired
    private CryptoCoinService cryptoCoinService;

    @GetMapping("/crypto/{name}")
    public ResponseEntity<CryptoCoin> handleRequest1(@PathVariable @Valid String name) throws ResourceNotFoundException {
        CryptoCoin coin = cryptoCoinService.getCryptoCoinByName(name);
        return ResponseEntity.ok(coin);
    }

    @GetMapping("/cryptos/{id}")
    public ResponseEntity<CryptoCoin> handleRequest4(@PathVariable @Valid Integer id) throws ResourceNotFoundException {
        CryptoCoin coin = cryptoCoinService.getCryptoCoinById(id);
        return ResponseEntity.ok(coin);
    }
    @GetMapping("/cryptos")
    public ResponseEntity<List<CryptoCoin>> getCryptoCoins(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "sortOrder", required = false) String sortOrder) {

        if (page != null && size != null && sortBy != null && sortOrder != null) {
            return ResponseEntity.ok(cryptoCoinService.getAllCryptoCoins(page, size, sortBy, sortOrder));
        } else if (page != null && size != null) {
            return ResponseEntity.ok(cryptoCoinService.getAllCryptoCoins(page, size,null,null));
        } else if (sortBy != null && sortOrder != null) {
            return ResponseEntity.ok(cryptoCoinService.getAllCryptoCoins(null,null,sortBy, sortOrder));
        } else {
            return ResponseEntity.ok(cryptoCoinService.getAllCryptoCoins(page, size, sortBy, sortOrder));
        }
    }

    @GetMapping("/search-cryptos")
    public ResponseEntity<List<CryptoCoin>> handleRequest3
            (@RequestParam(required = false) String name,
             @RequestParam(required = false) String symbol,
             @RequestParam(required = false) Double priceGreaterThan,
             @RequestParam(required = false) Double priceLessThan,
             @RequestParam(required = false) Double percentageChange24hGreaterThan,
             @RequestParam(required = false) Double percentageChange24hLessThan,
             @RequestParam(required = false) LocalDateTime dateAfter,
             @RequestParam(required = false) LocalDateTime startDate,
             @RequestParam(required = false) LocalDateTime endDate){

        return ResponseEntity.ok(cryptoCoinService.searchCryptoCoins(
                name,symbol,
                priceGreaterThan,priceLessThan,
                percentageChange24hGreaterThan,percentageChange24hLessThan,
                dateAfter,startDate,endDate));

//    @PostMapping(value = "/addcoin", consumes = "application/json")
//    public ResponseEntity<CryptoCoin> handleRequest4(@Valid @RequestBody CryptoCoin coin){
//        return ResponseEntity.ok(cryptoCoinService.addCryptoCoin(coin));
//    }
}
