package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.WatchListRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.specifications.CryptoCoinSpecification;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WatchListService {
    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private CryptoCoinService cryptoCoinService;

    public List<WatchList> getWatchList(){
        return watchListRepository.findAll();
    }

    public void addToWatchList(String coinName) throws ResourceNotFoundException {
        User user = authService.getUser();
        CryptoCoin cryptoCoin = cryptoCoinService.getCryptoCoinByName(coinName);
        WatchList watchList = watchListRepository.getWatchListByUser(user);
        if (watchList == null ){
            watchList = new WatchList();
            watchList.setUser(user);
            watchList.setCryptoCoins(new ArrayList<>());
        }
        watchList.addCryptoCoin(cryptoCoin);
        watchListRepository.save(watchList);
    }
}
