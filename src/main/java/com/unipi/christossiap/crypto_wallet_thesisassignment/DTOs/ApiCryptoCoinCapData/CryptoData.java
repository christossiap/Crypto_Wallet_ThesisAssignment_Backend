package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.ApiCryptoCoinCapData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"id","slug","num_market_pairs","date_added","tags","max_supply",
//        "circulating_supply","infinite_supply","platform","cmc_rank","self_reported_circulating_supply",
//        "self_reported_market_cap","tvl_ratio","last_updated"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoData {
    private String name;
    private String symbol;

    @JsonProperty("total_supply")
    private Long totalSupply;

    @JsonProperty("quote")
    private Quote quote;

}
