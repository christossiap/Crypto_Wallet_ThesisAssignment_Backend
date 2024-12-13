package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices.auth;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserRegisterDTO;
import com.unipi.christossiap.crypto_wallet_thesisassignment.configuration.auth.jwt.JwtAuthenticationResponse;
import com.unipi.christossiap.crypto_wallet_thesisassignment.configuration.auth.jwt.LoginRequest;
import com.unipi.christossiap.crypto_wallet_thesisassignment.configuration.auth.jwt.JwtTokenProvider;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.AuthException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
public class AuthController {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @GetMapping("/test")
    public ResponseEntity<?> handleRequesttest() throws ResourceNotFoundException {
        User user = authService.getUser();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> handleRequest(@RequestBody LoginRequest loginRequest) throws AccessDeniedException {

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch(Exception e) {
            throw new AccessDeniedException("Access Denied");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> handleRequest2(@Valid @RequestBody User user) throws ResourceNotFoundException {
        authService.registerUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/secret-mails")
    public ResponseEntity<String> handleRequest10(){
        authService.secretSantaEmails();
        return ResponseEntity.ok("Secret Santa email sent!!");
    }

    @PostMapping("/register-complete")
    public ResponseEntity<String> handleRequest3(@Valid @RequestBody Map<String,String> map){
        if (authService.registerComplete(map.get("Code"))){
            return ResponseEntity.ok("success");
        }else
            return ResponseEntity.ok("registration failed...");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> handleRequest5 (@Valid @RequestBody Map<String, String> map) throws ResourceNotFoundException {
        User user = authService.getUser();
        if (user==null)
            throw new AuthException("You are not logged in!");
        String password = map.get("password");
        authService.changePassword (user, password);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @PostMapping("/username-reminder")
//    public ResponseEntity<?> handleRequest6(@Valid @RequestBody Map<String,String> map) throws ResourceNotFoundException {
//        authService.userNameReminder(map.get("email"));
//        ResponseEntity.ok("Check you email!");
//    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<String> handleRequest4(){
        try {
            authService.deleteUser();
            return ResponseEntity.ok("User and related entities deleted successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
}