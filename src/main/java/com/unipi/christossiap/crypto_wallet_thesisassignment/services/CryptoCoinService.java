package com.unipi.christossiap.crypto_wallet_thesisassignment.services;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.CryptoCoinRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.specifications.CryptoCoinSpecification;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoCoinService {
    @Autowired
    private CryptoCoinRepository cryptoCoinRepository;



    public CryptoCoin getCryptoCoinByName(String name) throws ResourceNotFoundException {
        CryptoCoin coin = cryptoCoinRepository.findCryptoCoinByName(name);
        if (coin == null){
            throw new ResourceNotFoundException("Δεν βρέθηκε το συγκεκριμένο κρυπτονόμισμα!");
        }
        return coin;
    }


    @Cacheable(value = "my-cache", key = "'coin-' + #id",sync = true)
    public CryptoCoin getCryptoCoinById(Integer id) throws ResourceNotFoundException {
        CryptoCoin coin = cryptoCoinRepository.findCryptoCoinById(id);
        if (coin == null){
            throw new ResourceNotFoundException("Δεν βρέθηκε το συγκεκριμένο κρυπτονόμισμα!");
        }
        return coin;
    }

    public List<CryptoCoin> getAllCryptoCoins() throws ResourceNotFoundException {
        int page = 0;
        int size = 8;

        PageRequest pageable = PageRequest.of(page,size);
        Page<CryptoCoin> result = cryptoCoinRepository.findAll(pageable);

        if (result.isEmpty()){
            throw new ResourceNotFoundException("Δεν υπάρχουν κρυπτονομίσματα!");
        }
        return result.getContent();
    }

    public List<CryptoCoin> searchCryptoCoins(String name,String symbol, Double priceGreaterThan, Double priceLessThan){
        Specification<CryptoCoin> spec = Specification.where(null);

        if (name!=null)
            spec = spec.and(CryptoCoinSpecification.hasName(name));

        if (symbol!=null)
            spec = spec.and(CryptoCoinSpecification.hasSymbol(symbol));

        if (priceGreaterThan!=null)
            spec = spec.and(CryptoCoinSpecification.hasPriceGreaterThan(priceGreaterThan));

        if (priceLessThan!=null)
            spec = spec.and(CryptoCoinSpecification.hasPriceLessThan(priceLessThan));

        List<CryptoCoin> cryptoCoins = cryptoCoinRepository.findAll(spec);

        return cryptoCoins;
    }
}
