package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.watchListDTOs.WatchListInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.WatchListService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api", produces = "application/json")
public class WatchListController {
    @Autowired
    private WatchListService watchListService;

    @GetMapping("/my-watchlist")
    public ResponseEntity<WatchListInfo> handleRequest6(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) throws ResourceNotFoundException {
        return ResponseEntity.ok(watchListService.getUserWatchList(page,size));
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
        watchListService.clearWatchList();
        return ResponseEntity.ok("Watchlist cleared.");
    }
}
