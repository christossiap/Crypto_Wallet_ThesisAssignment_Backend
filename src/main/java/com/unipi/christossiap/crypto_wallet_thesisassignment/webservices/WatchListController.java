package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.cryptocoinDTOs.CoinNameRequest;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.watchListDTOs.WatchListInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.WatchListService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/watchlist", produces = "application/json")
public class WatchListController {
    @Autowired
    private WatchListService watchListService;

    @GetMapping
    public ResponseEntity<WatchListInfo> handleRequest1(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(watchListService.getUserWatchList(page,size));
    }

    @PostMapping
    public ResponseEntity<String> handleRequest2(@Valid @RequestBody CoinNameRequest request) throws ResourceNotFoundException {
        watchListService.addCoinToWatchList(request.name());
        return ResponseEntity.ok("Successfully added.");
    }
    @DeleteMapping
    public ResponseEntity<String> handleRequest3(@Valid @RequestBody CoinNameRequest request) throws ResourceNotFoundException {
        watchListService.deleteCoinFromWatchList(request.name());
        return ResponseEntity.ok("Successfully deleted.");
    }
    @DeleteMapping("/clear")
    public ResponseEntity<String> handleRequest4() throws ResourceNotFoundException {
        watchListService.clearWatchList();
        return ResponseEntity.ok("Successfully cleared.");
    }
}
