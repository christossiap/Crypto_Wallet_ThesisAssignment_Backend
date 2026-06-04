package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.profileDTOs.EditUserProfileRequest;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.profileDTOs.UserProfileInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.UserProfile;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.UserProfileRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth.AuthService;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AuthService authService;

    public UserProfileInfo getAllUserInfo() throws ResourceNotFoundException {
        User user = authService.getUser();
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user: " + user.getId()));

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
    public void editUserProfile(EditUserProfileRequest userInfo) throws ResourceNotFoundException {
        User user = authService.getUser();
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user: " + user.getId()));

        if (userInfo.firstName() != null) {
            userProfile.setFirstName(userInfo.firstName());
        }
        if (userInfo.lastName() != null) {
            userProfile.setLastName(userInfo.lastName());
        }
        if (userInfo.phoneNumber() != null) {
            userProfile.setPhoneNumber(userInfo.phoneNumber());
        }
        if (userInfo.country() != null) {
            userProfile.setCountry(userInfo.country());
        }
        if (userInfo.city() != null) {
            userProfile.setCity(userInfo.city());
        }
        if (userInfo.addressLine() != null) {
            userProfile.setAddressLine(userInfo.addressLine());
        }
        if (userInfo.postalCode() != null) {
            userProfile.setPostalCode(userInfo.postalCode());
        }
        if (userInfo.bio() != null) {
            userProfile.setBio(userInfo.bio());
        }
        userProfileRepository.save(userProfile);
    }

}
