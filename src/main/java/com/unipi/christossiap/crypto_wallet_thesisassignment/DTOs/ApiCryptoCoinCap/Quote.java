package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.ApiCryptoCoinCap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    @JsonProperty("USD")
    private QuoteDetails usd;
}
