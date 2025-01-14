package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices.auth;
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
import java.util.HashMap;
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
    public ResponseEntity<?> handleRequest2(@Valid @RequestBody User user){
        authService.registerUser(user);
        return ResponseEntity.ok("success");
    }


    @PostMapping("/register-complete")
    public ResponseEntity<String> handleRequest3(@Valid @RequestBody Map<String,String> map){
        if (authService.registerComplete(map.get("Code"))){
            return ResponseEntity.ok("success");
        }else
            return ResponseEntity.ok("registration failed...Please check the details you provided!");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> handleRequest5 (@Valid @RequestBody Map<String, String> map){
        User user = authService.getUser();
        if (user==null)
            throw new AuthException("You are not logged in!");
        String password = map.get("password");
        authService.changePassword (user, password);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/username-reminder")
    public ResponseEntity<?> handleRequest6(@Valid @RequestBody Map<String,String> map){
        User user = authService.userNameReminder(map.get("email"));
        map = new HashMap<>();
        map.put("username: ",user.getUsername());
        return ResponseEntity.ok(map);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> handleRequest7(@Valid @RequestBody Map<String,String> map){
        authService.passwordReminder(map.get("username"));
        return ResponseEntity.ok("A reminder message has been sent to your email!");
    }

    @PostMapping("/confirm-reset-password")
    public ResponseEntity<?> handleRequest8(@Valid @RequestBody Map<String,String> map){
        try {
            authService.passwordResetConfirmation(map.get("username"),map.get("code"),map.get("password"));
        } catch (Exception e) {
            throw new RuntimeException("Please check the details you provided!");
        }
        return ResponseEntity.ok("The password was changed successfully!");
    }

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