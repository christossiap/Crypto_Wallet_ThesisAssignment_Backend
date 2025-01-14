package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.EditUserInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserProfileInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.UserProfile;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.UserProfileService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api",produces = "application/json")
@Validated
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/user-profile")
    public ResponseEntity<UserProfileInfo> handleRequest(){
        return ResponseEntity.ok(userProfileService.getAllUserInfo());
    }


    @PutMapping("/edit")
    public ResponseEntity<String> editUserProfile(@Valid @RequestBody EditUserInfo userInfos){
        userProfileService.editUserProfile(userInfos);
        return ResponseEntity.ok("User profile updated successfully.");
    }
}
