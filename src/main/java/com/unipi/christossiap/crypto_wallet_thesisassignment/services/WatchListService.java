package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.WatchListInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.WatchListRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.specifications.CryptoCoinSpecification;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WatchListService {
    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private CryptoCoinService cryptoCoinService;
//    private static final Logger logger = LoggerFactory.getLogger(WatchListService.class);

    public Boolean isWatchListNull(WatchList watchList){
        return watchList == null;
    }
    public void addNewWatchListIfNecessary(User user, WatchList watchList){
        if (isWatchListNull(watchList)){
            WatchList newWatchlist = new WatchList();
            user.addWatchList(newWatchlist);
            authService.saveUser(user);
        }
    }

    public List<WatchListInfo> getUserWatchList() throws ResourceNotFoundException {
        User user = authService.getUser();
        List<Object[]> result = watchListRepository.getWatchListInfo(user.getId());

        // Group data by username and aggregate coins
        Map<String, List<String>> groupedData = result.stream()
                .collect(Collectors.groupingBy(
                        row -> (String) row[0], // Key: username
                        Collectors.mapping(row -> (String) row[1], Collectors.toList()) // Value: List of coin names
                ));

        // Convert grouped data into a list of WatchListInfo objects
        return groupedData.entrySet().stream()
                .map(entry -> new WatchListInfo(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public void addCoinToWatchList(String coinName) throws ResourceNotFoundException {
        User user = authService.getUser();
        addNewWatchListIfNecessary(user,watchListRepository.getWatchListByUserId(user.getId()));
        WatchList watchList = watchListRepository.getWatchListByUserId(user.getId());
        watchList.addCryptoCoin(cryptoCoinService.getCryptoCoinByName(coinName));
        watchListRepository.save(watchList);
    }

    public void deleteCoinFromWatchList(String coinName) throws ResourceNotFoundException {
//        User user = authService.getUser();  Κάνει check για null WatchList και αν είναι την κάνει Initialize στο User
//        addNewWatchListIfNecessary(user,watchListRepository.getWatchListByUserId(user.getId()));
        WatchList watchList = watchListRepository.getWatchListByUserId(authService.getUser().getId());
        watchList.removeCryptoCoin(cryptoCoinService.getCryptoCoinByName(coinName));
        watchListRepository.save(watchList);
    }

    public void clearWatchlist() throws ResourceNotFoundException {
        User user = authService.getUser();
        watchListRepository.clearWatchListByUserId(user.getId());
    }


}
