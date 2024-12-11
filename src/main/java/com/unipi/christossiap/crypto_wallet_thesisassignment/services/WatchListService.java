package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.watchListDTOs.WatchListInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.WatchListRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(WatchListService.class);

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
    public WatchListInfo getUserWatchList(int page, int size) throws ResourceNotFoundException {
        User user = authService.getUser();
        PageRequest pageable = PageRequest.of(page,size);
        List<Object[]> result = watchListRepository.getWatchListInfo(user.getId(),pageable);

        List<String> coins = result.stream()
                .map(row -> (String) row[1])
                .collect(Collectors.toList());

        return new WatchListInfo(user.getUsername(), coins);
    }


    @Transactional
    public void addCoinToWatchList(String coinName) throws ResourceNotFoundException {
        User user = authService.getUser();
        addNewWatchListIfNecessary(user,watchListRepository.getWatchListByUserId(user.getId()));
        WatchList watchList = watchListRepository.getWatchListByUserId(user.getId());
        watchList.addCryptoCoin(cryptoCoinService.getCryptoCoinByName(coinName));
        watchListRepository.save(watchList);
    }


    @Transactional
    public void deleteCoinFromWatchList(String coinName) throws ResourceNotFoundException {
        WatchList watchList = watchListRepository.getWatchListByUserId(authService.getUser().getId());
        watchList.removeCryptoCoin(cryptoCoinService.getCryptoCoinByName(coinName));
        watchListRepository.save(watchList);
    }

//    public void clearWatchlist() throws ResourceNotFoundException {
//        User user = authService.getUser();
//        WatchList watchList = watchListRepository.getWatchListByUserId(authService.getUser().getId());
//        List<String> coins = watchListRepository.getWatchListCryptoCoinsByUserId(user.getId());
//        logger.info(String.valueOf(coins));
//        for (String coin : coins){
//            logger.info(String.valueOf(coin));
//            watchList.removeCryptoCoin(cryptoCoinService.getCryptoCoinByName(coin));
//
//        }
//        watchListRepository.save(watchList);
//        logger.info(String.valueOf(coins));
//    }

    @Transactional
    public void clearWatchList() throws ResourceNotFoundException {
        User user = authService.getUser();
        watchListRepository.clearWatchListByUserId(user.getId());
    }


}
