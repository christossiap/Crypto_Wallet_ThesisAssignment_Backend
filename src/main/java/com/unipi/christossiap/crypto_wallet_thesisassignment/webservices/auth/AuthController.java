package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices.auth;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.authDTOs.*;
import com.unipi.christossiap.crypto_wallet_thesisassignment.configuration.auth.jwt.JwtAuthenticationResponse;
import com.unipi.christossiap.crypto_wallet_thesisassignment.configuration.auth.jwt.JwtTokenProvider;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.AuthException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.UserValidationExceptions;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );
        } catch(Exception e) {
            log.info(loginRequest.username(), loginRequest.password());
            throw new AccessDeniedException("Access Denied");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> handleRequest2(@Valid @RequestBody RegisterRequest registerRequest){
        authService.registerUser(registerRequest);
        return ResponseEntity.ok("success");
    }


    @PostMapping("/register-complete")
    public ResponseEntity<String> handleRequest3(@Valid @RequestBody CodeRequest codeRequest){
        authService.registerComplete(codeRequest.code());
        return ResponseEntity.ok("Registration has been successful.");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> handleRequest5 (
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest){
        authService.changePassword (changePasswordRequest.password());
        return ResponseEntity.ok("Password successfully changed");
    }

    @PostMapping("/change-username")

    public ResponseEntity<String> handleRequest9 (
            @Valid @RequestBody ChangeUsernameRequest changeUsernameRequest){
        authService.changeUsername(changeUsernameRequest.username());
        return ResponseEntity.ok("Username successfully changed!");
    }

    @PostMapping("/change-email")
    public ResponseEntity<String> handleRequest10 (
            @Valid @RequestBody ChangeEmailRequest changeEmailRequest){
        authService.emailChange(changeEmailRequest.email());
        return ResponseEntity.ok("Email successfully changed!");
    }

    @PostMapping("/username-reminder")
    public ResponseEntity<?> handleRequest6(@Valid @RequestBody EmailRequest emailRequest){
        authService.userNameReminder(emailRequest.email());
        return ResponseEntity.ok("A reminder message has been sent to registered email!");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> handleRequest7(@Valid @RequestBody EmailRequest emailRequest){
        authService.passwordReminder(emailRequest.email());
        return ResponseEntity.ok("A reminder message has been sent to registered email!");
    }

    @PostMapping("/confirm-reset-password")
    public ResponseEntity<?> handleRequest8(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
        authService.passwordResetConfirmation(
                passwordResetRequest.username(),
                passwordResetRequest.code(),
                passwordResetRequest.password());
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