package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.WatchListInfo;
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

    @GetMapping("/my-watchlist")
    public ResponseEntity<List<WatchListInfo>> handleRequest4() throws ResourceNotFoundException {
        return ResponseEntity.ok(watchListService.getUserWatchList());
    }

    @PostMapping(value = "/add-coin-to-watchlist", consumes = "application/json")
    public ResponseEntity<String> handleRequest3(@Valid @RequestBody Map<String, String> coin) throws ResourceNotFoundException {
        watchListService.addCoinToWatchList(coin.get("name"));
        return ResponseEntity.ok("Successfully added..!");
    }
    @DeleteMapping(value = "/delete-coin-from-watchlist")
    public ResponseEntity<String> handleRequest4(@Valid @RequestBody Map<String, String> coin) throws ResourceNotFoundException {
        watchListService.deleteCoinFromWatchList(coin.get("name"));
        return ResponseEntity.ok("Deleted..");
    }
    @DeleteMapping("/clear-watchlist")
    public ResponseEntity<String> clearWatchlist() throws ResourceNotFoundException {
        watchListService.clearWatchlist();
        return ResponseEntity.ok("Watchlist cleared.");
    }
}
