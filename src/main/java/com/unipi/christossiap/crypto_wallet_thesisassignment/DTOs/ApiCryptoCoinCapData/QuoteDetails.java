package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.ApiCryptoCoinCapData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"volume_24h","volume_change_24h","percent_change_1h","percent_change_7d",
//        "percent_change_30d","percent_change_60d","percent_change_90d","market_cap_dominance",
//        "fully_diluted_market_cap","tvl"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteDetails {
    private Double price;

    @JsonProperty("percent_change_24h")
    private Double percentChange24h;

    @JsonProperty("market_cap")
    private Double marketCap;

    @JsonProperty("last_updated")
    private String lastUpdated;
}
