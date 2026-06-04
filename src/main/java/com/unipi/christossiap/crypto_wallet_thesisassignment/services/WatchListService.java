package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.watchListDTOs.WatchListInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.WatchListRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchListService {
    @Autowired
    private WatchListRepository watchListRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private CryptoCoinService cryptoCoinService;
    public WatchListInfo getUserWatchList(int page, int size){
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
        WatchList watchList = getOrCreateWatchList(user);
        CryptoCoin coin = cryptoCoinService.getCryptoCoinByName(coinName);
        watchList.addCryptoCoin(coin);
        watchListRepository.save(watchList);
    }

    @Transactional
    public void deleteCoinFromWatchList(String coinName) throws ResourceNotFoundException {
        User user = authService.getUser();
        WatchList watchList = watchListRepository.findWatchListByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("WatchList not found"));
        watchList.removeCryptoCoin(cryptoCoinService.getCryptoCoinByName(coinName));
        watchListRepository.save(watchList);
    }

    @Transactional
    public void clearWatchList() throws ResourceNotFoundException {
        User user = authService.getUser();
        WatchList watchList = watchListRepository.findWatchListByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("WatchList not found"));
        watchList.getCryptoCoins().clear();
        watchListRepository.save(watchList);
    }

    private WatchList getOrCreateWatchList(User user) throws ResourceNotFoundException {
        WatchList wl = watchListRepository.findWatchListByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("WatchList not found"));
        if (wl == null) {
            wl = new WatchList();
            user.addWatchList(wl);
            authService.saveUser(user);
        }
        return wl;
    }
}
