package com.unipi.christossiap.crypto_wallet_thesisassignment.services.ApiCryptoCoinCapService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.ApiCryptoCoinCapData.CryptoApiResponse;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.ApiCryptoCoinCapData.CryptoData;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoin;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.CryptoCoinHistory;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.CryptoCoinRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.CryptoCoinHistoryService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.CryptoCoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
@Slf4j
@Service
public class CryptoApiService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CryptoCoinService cryptoCoinService;
    @Autowired
    private CryptoCoinRepository cryptoCoinRepository;
    @Autowired
    private CryptoCoinHistoryService cryptoCoinHistoryService;

    @Value("${api.url}")
    private String apiUrl;

//    @Scheduled(fixedRate = 60000) // 1 minute in milliseconds
    //@Scheduled(fixedRate = 3600000) // 1 hour in milliseconds
    //@Scheduled(fixedRate = 86400000) // 1 day in milliseconds
//@Caching(evict = {
//        @CacheEvict(value = "my-cache", key = "'coin' + #coin.name"),
//        @CacheEvict(value = "my-cache", key = "'coin-' + #coin.id")
//})
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
                CryptoCoin oldCoin = cryptoCoinRepository.findCryptoCoinByName(cryptoData.getName())
                        .orElse(null);
                if (oldCoin != null) {
                    oldCoin.setTotalSupply(cryptoData.getTotalSupply());

                    // Use BigDecimal for safe rounding
                    BigDecimal price = BigDecimal.valueOf(cryptoData.getQuote().getUsd().getPrice())
                            .setScale(2, RoundingMode.HALF_UP);
                    oldCoin.setPrice(price.doubleValue());

                    BigDecimal percentChange24h = BigDecimal.valueOf(cryptoData.getQuote().getUsd().getPercentChange24h())
                            .setScale(2, RoundingMode.HALF_UP);
                    oldCoin.setPercentChange24h(percentChange24h.doubleValue());

                    oldCoin.setMarketCap(cryptoData.getQuote().getUsd().getMarketCap());

                    // Use Instant to parse UTC with Z or OffsetDateTime for offsets
                    String lastUpdatedString = cryptoData.getQuote().getUsd().getLastUpdated();
                    Instant instant = Instant.parse(lastUpdatedString); // Handles Z or offsets
                    oldCoin.setLastUpdated(instant.atOffset(OffsetDateTime.now().getOffset()).toLocalDateTime());

                    cryptoCoinService.saveCryptoCoin(oldCoin);

                    CryptoCoinHistory coinHistory = new CryptoCoinHistory();
                    coinHistory.setTotalSupply(cryptoData.getTotalSupply());
                    coinHistory.setMarketCap(cryptoData.getQuote().getUsd().getMarketCap());
                    coinHistory.setPercentChange24h(percentChange24h.doubleValue());
                    coinHistory.setPrice(price.doubleValue());
                    coinHistory.setLastUpdated(instant.atOffset(OffsetDateTime.now().getOffset()).toLocalDateTime());
                    coinHistory.setCryptoCoin(oldCoin);
                    cryptoCoinHistoryService.saveCryptoCoinHistory(coinHistory);

                } else {
                    CryptoCoin coin = new CryptoCoin();
                    coin.setName(cryptoData.getName());
                    coin.setSymbol(cryptoData.getSymbol());
                    coin.setTotalSupply(cryptoData.getTotalSupply());

                    BigDecimal price = BigDecimal.valueOf(cryptoData.getQuote().getUsd().getPrice())
                            .setScale(2, RoundingMode.HALF_UP);
                    coin.setPrice(price.doubleValue());

                    BigDecimal percentChange24h = BigDecimal.valueOf(cryptoData.getQuote().getUsd().getPercentChange24h())
                            .setScale(2, RoundingMode.HALF_UP);
                    coin.setPercentChange24h(percentChange24h.doubleValue());

                    coin.setMarketCap(cryptoData.getQuote().getUsd().getMarketCap());

                    // Use Instant to parse UTC with Z or OffsetDateTime for offsets
                    String lastUpdatedString = cryptoData.getQuote().getUsd().getLastUpdated();
                    Instant instant = Instant.parse(lastUpdatedString); // Handles Z or offsets
                    coin.setLastUpdated(instant.atOffset(OffsetDateTime.now().getOffset()).toLocalDateTime());

                    cryptoCoinService.saveCryptoCoin(coin);

                    CryptoCoinHistory coinHistory = new CryptoCoinHistory();
                    coinHistory.setTotalSupply(cryptoData.getTotalSupply());
                    coinHistory.setMarketCap(cryptoData.getQuote().getUsd().getMarketCap());
                    coinHistory.setPercentChange24h(percentChange24h.doubleValue());
                    coinHistory.setPrice(price.doubleValue());
                    coinHistory.setLastUpdated(instant.atOffset(OffsetDateTime.now().getOffset()).toLocalDateTime());
                    coinHistory.setCryptoCoin(coin);
                    cryptoCoinHistoryService.saveCryptoCoinHistory(coinHistory);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
