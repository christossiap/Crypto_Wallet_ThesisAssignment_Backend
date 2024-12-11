package com.unipi.christossiap.crypto_wallet_thesisassignment.services;


import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.CryptoCoinRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.specifications.CryptoCoinSpecification;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoCoinService {
    @Autowired
    private CryptoCoinRepository cryptoCoinRepository;

    public void saveCryptoCoin(CryptoCoin coin){cryptoCoinRepository.save(coin);}

//    @Cacheable(value = "my-cache", key = "'coin' + #name", sync = true)
    public CryptoCoin getCryptoCoinByName(String name) throws ResourceNotFoundException {
        CryptoCoin coin = cryptoCoinRepository.findCryptoCoinByName(name);
        if (coin == null){
            throw new ResourceNotFoundException("Δεν βρέθηκε το συγκεκριμένο κρυπτονόμισμα!");
        }
        return coin;
    }

//    @Cacheable(value = "my-cache", key = "'coin-' + #id",sync = true)
    public CryptoCoin getCryptoCoinById(Integer id) throws ResourceNotFoundException {
        CryptoCoin coin = cryptoCoinRepository.findCryptoCoinById(id);
        if (coin == null){
            throw new ResourceNotFoundException("Δεν βρέθηκε το συγκεκριμένο κρυπτονόμισμα!");
        }
        return coin;
    }
    public List<CryptoCoin> getAllCryptoCoins(Integer page, Integer size, String sortBy, String sortOrder) {

        Sort sort = (sortBy != null && sortOrder != null)
                ? Sort.by(Sort.Direction.fromString(sortOrder), sortBy)
                : Sort.unsorted();

        if (page != null && size != null) {
            PageRequest pageable = PageRequest.of(page, size, sort);
            Page<CryptoCoin> result = cryptoCoinRepository.findAll(pageable);
            return result.hasContent() ? result.getContent() : new ArrayList<>();
        }

        return sort.isSorted()
                ? cryptoCoinRepository.findAll(sort)
                : cryptoCoinRepository.findAll();
    }


    public List<CryptoCoin> searchCryptoCoins(String name, String symbol, Double priceGreaterThan, Double priceLessThan,
                                              Double percentageChange24hGreaterThan, Double percentageChange24hLessThan,
                                              LocalDateTime dateAfter,LocalDateTime startDate,LocalDateTime endDate){
        Specification<CryptoCoin> spec = Specification.where(null);

        if (name!=null)
            spec = spec.and(CryptoCoinSpecification.hasName(name));

        if (symbol!=null)
            spec = spec.and(CryptoCoinSpecification.hasSymbol(symbol));

        if (priceGreaterThan!=null)
            spec = spec.and(CryptoCoinSpecification.hasPriceGreaterThan(priceGreaterThan));

        if (priceLessThan!=null)
            spec = spec.and(CryptoCoinSpecification.hasPriceLessThan(priceLessThan));

        if (percentageChange24hGreaterThan!=null)
            spec = spec.and(CryptoCoinSpecification.hasPercentageChange24hGreaterThen(percentageChange24hGreaterThan));

        if (percentageChange24hLessThan!=null)
            spec = spec.and(CryptoCoinSpecification.hasPercentageChange24hLessThen(percentageChange24hLessThan));

        if (dateAfter!=null)
            spec = spec.and(CryptoCoinSpecification.updatedAfter(dateAfter));

        if (startDate!=null && endDate!=null)
            spec = spec.and(CryptoCoinSpecification.updatedBetween(startDate,endDate));

        List<CryptoCoin> cryptoCoins = cryptoCoinRepository.findAll(spec);

        return cryptoCoins;
    }
}
