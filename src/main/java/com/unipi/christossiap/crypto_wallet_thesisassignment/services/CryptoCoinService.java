package com.unipi.christossiap.crypto_wallet_thesisassignment.services;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.CryptoCoinRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoCoinService {
    @Autowired
    private CryptoCoinRepository cryptoCoinRepository;

    public CryptoCoin getCryptoCoin(String name) throws ResourceNotFoundException {
        CryptoCoin coin = cryptoCoinRepository.findCryptoCoinByName(name);
        if (coin == null){
            throw new ResourceNotFoundException("Δεν βρέθηκε το συγκεκριμένο κρυπτονόμισμα!");
        }
        return coin;
    }

    public List<CryptoCoin> getAllCryptoCoins() throws ResourceNotFoundException {
        List<CryptoCoin> list = cryptoCoinRepository.findAll();
        if (list.isEmpty()){
            throw new ResourceNotFoundException("Δεν υπάρχουν κρυπτονομίσματα!");
        }
        return list;
    }
}
