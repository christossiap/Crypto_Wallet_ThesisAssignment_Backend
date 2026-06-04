package com.unipi.christossiap.crypto_wallet_thesisassignment.webservices;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.profileDTOs.EditUserProfileRequest;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.profileDTOs.UserProfileInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.UserProfileService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/profile",produces = "application/json")
@Validated
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping
    public ResponseEntity<UserProfileInfo> getProfile() throws ResourceNotFoundException {
        return ResponseEntity.ok(userProfileService.getAllUserInfo());
    }

    @PatchMapping("/edit")
    public ResponseEntity<String> editProfile(@Valid @RequestBody EditUserProfileRequest editUserInfo) throws ResourceNotFoundException {
        userProfileService.editUserProfile(editUserInfo);
        return ResponseEntity.ok("User profile updated successfully.");
    }
}
