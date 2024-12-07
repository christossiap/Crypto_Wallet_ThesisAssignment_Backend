package com.unipi.christossiap.crypto_wallet_thesisassignment.services.ApiCryptoCoinCap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.ApiCryptoCoinCap.CryptoApiResponse;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.ApiCryptoCoinCap.CryptoData;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.CryptoCoinRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.CryptoCoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CryptoApiService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CryptoCoinService cryptoCoinService;
    @Autowired
    private CryptoCoinRepository cryptoCoinRepository;

    private static final Logger logger = LoggerFactory.getLogger(CryptoCoinService.class);

    @Value("${api.url}")
    private String apiUrl;


    //@Scheduled(fixedRate = 60000) // 1 minute in milliseconds
    //@Scheduled(fixedRate = 3600000) // 1 hour in milliseconds
    //@Scheduled(fixedRate = 86400000) // 1 day in milliseconds
    public void fetchAndSaveCryptoData() {
        String param1 = "limit=30";
        String url = String.format("%s?%s", apiUrl, param1);

        // Create Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("X-CMC_PRO_API_KEY", "REDACTED_CMC_API_KEY");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        try {
            // Parse JSON
            ObjectMapper objectMapper = new ObjectMapper();
            CryptoApiResponse apiResponse = objectMapper.readValue(response.getBody(), CryptoApiResponse.class);

            // Map and Save
            List<CryptoData> cryptoDataList = apiResponse.getData();

            for (CryptoData cryptoData : cryptoDataList) {
                CryptoCoin oldCoin = cryptoCoinRepository.findCryptoCoinByName(cryptoData.getName());
                if (oldCoin != null){
                    oldCoin.setTotalSupply(cryptoData.getTotalSupply());
                    oldCoin.setPrice(cryptoData.getQuote().getUsd().getPrice());
                    oldCoin.setPercentChange24h(cryptoData.getQuote().getUsd().getPercentChange24h());
                    oldCoin.setMarketCap(cryptoData.getQuote().getUsd().getMarketCap());
                    oldCoin.setLastUpdated(LocalDateTime.parse(cryptoData.getQuote().getUsd().getLastUpdated(), DateTimeFormatter.ISO_DATE_TIME));
                    cryptoCoinService.saveCryptoCoin(oldCoin);
                }else {
                    CryptoCoin coin = new CryptoCoin();
                    coin.setName(cryptoData.getName());
                    coin.setSymbol(cryptoData.getSymbol());
                    coin.setTotalSupply(cryptoData.getTotalSupply());
                    coin.setPrice(cryptoData.getQuote().getUsd().getPrice());
                    coin.setPercentChange24h(cryptoData.getQuote().getUsd().getPercentChange24h());
                    coin.setMarketCap(cryptoData.getQuote().getUsd().getMarketCap());
                    coin.setLastUpdated(LocalDateTime.parse(cryptoData.getQuote().getUsd().getLastUpdated(), DateTimeFormatter.ISO_DATE_TIME));
                    cryptoCoinService.addCryptoCoin(coin);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
