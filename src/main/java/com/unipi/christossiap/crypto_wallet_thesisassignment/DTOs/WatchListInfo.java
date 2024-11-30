package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchListInfo {
    private String username;
    private List<String> coins;
}
