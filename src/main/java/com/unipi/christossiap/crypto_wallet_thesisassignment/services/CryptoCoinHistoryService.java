package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoinHistory;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.CryptoCoinHistoryRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoCoinHistoryService {
    @Autowired
    private CryptoCoinHistoryRepository cryptoCoinHistoryRepository;
    @Autowired
    private CryptoCoinService cryptoCoinService;

    public void saveCryptoCoinHistory(CryptoCoinHistory coinHistory){cryptoCoinHistoryRepository.save(coinHistory);}

    public List<CryptoCoinHistory> getCryptoCoinHistory(String coinName) throws ResourceNotFoundException {
        CryptoCoin coin = cryptoCoinService.getCryptoCoinByName(coinName);
        if (coin == null){throw new ResourceNotFoundException("This coin doesnt exist..!");}
        return cryptoCoinHistoryRepository.findAllByCryptoCoinId(coin.getId());
    }
}
