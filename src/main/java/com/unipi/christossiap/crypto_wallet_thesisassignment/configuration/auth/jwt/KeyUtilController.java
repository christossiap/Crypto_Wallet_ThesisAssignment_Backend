package com.unipi.christossiap.crypto_wallet_thesisassignment.configuration.auth.jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@RestController
@RequestMapping("/api")
public class KeyUtilController {

    @GetMapping("/create-key")
    public ResponseEntity<?> handleRequest() {
        String algorithm = "HmacSHA512";

        SecretKey secretKey = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            secretKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secret key", e);
        }

        byte[] keyBytes = secretKey.getEncoded();
        String keyInBase64 = Base64.getEncoder().encodeToString(keyBytes);

        return ResponseEntity.ok(keyInBase64);
    }

    public static SecretKey base64ToSecretKey(String base64Key, String algorithm) {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(keyBytes, algorithm);
    }
}


