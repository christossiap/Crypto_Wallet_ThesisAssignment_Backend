package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices.auth;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.webservices.auth.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/api", produces = "application/json")
public class UserController {
    @Autowired
    private AuthService authService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> handleRequest1(){
        List<User> users = authService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/userdetails")
    public ResponseEntity<List<UserInfo>> handleRequest2(){
        List<UserInfo> userInfos = authService.getAllUserDetails();
        return ResponseEntity.ok(userInfos);
    }

    @GetMapping("/user")
    public ResponseEntity<User> handleRequest3() throws ResourceNotFoundException {
        return ResponseEntity.ok(authService.getUser());

    }
}
