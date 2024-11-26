package com.unipi.christossiap.crypto_wallet_thesisassignment.services.associations;


import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.associations.CryptoCoinPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CryptoCoinPortfolioService {
    @Autowired
    private CryptoCoinPortfolioRepository cryptoCoinPortfolioRepository;
}
