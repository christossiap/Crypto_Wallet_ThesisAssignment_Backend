package com.unipi.christossiap.crypto_wallet_thesisassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class CryptoWalletThesisAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoWalletThesisAssignmentApplication.class, args);
    }

//    // Define a RestTemplate bean
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

}
