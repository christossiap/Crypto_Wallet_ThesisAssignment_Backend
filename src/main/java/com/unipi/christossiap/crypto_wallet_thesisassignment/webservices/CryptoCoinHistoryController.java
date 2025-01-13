package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoinHistory;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.CryptoCoinHistoryService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api",produces = "application/json")
public class CryptoCoinHistoryController {
    @Autowired
    private CryptoCoinHistoryService cryptoCoinHistoryService;

    @PostMapping("/crypto-history")
    public ResponseEntity<List<CryptoCoinHistory>> handleRequest1(@RequestBody Map<String,String> map)throws ResourceNotFoundException {
        List<CryptoCoinHistory> coinHistoryList = cryptoCoinHistoryService.getCryptoCoinHistory(map.get("name"));
        return ResponseEntity.ok(coinHistoryList);
    }
}
