package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.EditUserInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserProfileInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.UserProfile;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.UserProfileRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Map;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private AuthService authService;

    public UserProfileInfo getAllUserInfo(){
        User user = authService.getUser();
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(user.getId());
        return new UserProfileInfo(
                user.getUsername(),
                user.getEmail(),
                userProfile.getFirstName(),
                userProfile.getLastName(),
                userProfile.getPhoneNumber(),
                userProfile.getCountry(),
                userProfile.getCity(),
                userProfile.getAddressLine(),
                userProfile.getPostalCode(),
                userProfile.getBio()
        );
    }

    @Transactional
    public void editUserProfile(EditUserInfo userInfo){
        User user = authService.getUser();
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(user.getId());

        if (userInfo.getUsername() != null) {
            authService.usernameChange(user,userInfo.getUsername());
        }
        if (userInfo.getEmail() != null) {
            authService.emailChange(user,userInfo.getEmail());
        }
        if (userInfo.getPassword() != null) {
            authService.changePassword(user,userInfo.getPassword());
        }
        if (userInfo.getFirstName() != null) {
            userProfile.setFirstName(userInfo.getFirstName());
        }
        if (userInfo.getLastName() != null) {
            userProfile.setLastName(userInfo.getLastName());
        }
        if (userInfo.getPhoneNumber() != null) {
            userProfile.setPhoneNumber(userInfo.getPhoneNumber());
        }
        if (userInfo.getCountry() != null) {
            userProfile.setCountry(userInfo.getCountry());
        }
        if (userInfo.getCity() != null) {
            userProfile.setCity(userInfo.getCity());
        }
        if (userInfo.getAddressLine() != null) {
            userProfile.setAddressLine(userInfo.getAddressLine());
        }
        if (userInfo.getPostalCode() != null) {
            userProfile.setPostalCode(userInfo.getPostalCode());
        }
        if (userInfo.getBio() != null) {
            userProfile.setBio(userInfo.getBio());
        }
        userProfileRepository.save(userProfile);
    }
}
