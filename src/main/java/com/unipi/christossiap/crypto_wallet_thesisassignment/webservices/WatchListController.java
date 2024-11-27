package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.CryptoCoinService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.WatchListService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api", produces = "application/json")
public class WatchListController {
    @Autowired
    private WatchListService watchListService;

    @GetMapping("/watchlists")
    public ResponseEntity<List<WatchList>> handleRequest2() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Name: " + authentication.getName());
        System.out.println("credentials: " + authentication.getCredentials());
        System.out.println("authorities: " + authentication.getAuthorities());
        System.out.println("principal: " + authentication.getPrincipal());
        System.out.println("details: " + authentication.getDetails());
        return new ResponseEntity<>(watchListService.getWatchList(), HttpStatus.OK);
    }

    @PostMapping(value = "/addcointowatchlist", consumes = "application/json")
    public ResponseEntity<String> handleRequest3(@Valid @RequestBody Map<String,String> coin) throws ResourceNotFoundException {
        watchListService.addToWatchList(coin.get("name"));
        return ResponseEntity.ok("Successfully added..!");
    }
}
