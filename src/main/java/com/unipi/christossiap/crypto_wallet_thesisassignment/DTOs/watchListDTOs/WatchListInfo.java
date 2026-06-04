package com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.watchListDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WatchListInfo {
    private String username;
    private List<String> coins;
}
