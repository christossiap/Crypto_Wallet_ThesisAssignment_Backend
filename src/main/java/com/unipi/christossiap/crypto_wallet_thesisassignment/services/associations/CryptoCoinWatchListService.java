package com.unipi.christossiap.crypto_wallet_thesisassignment.services.associations;


import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.associations.CryptoCoinWatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CryptoCoinWatchListService {
    @Autowired
    private CryptoCoinWatchListRepository cryptoCoinWatchListRepository;
}
