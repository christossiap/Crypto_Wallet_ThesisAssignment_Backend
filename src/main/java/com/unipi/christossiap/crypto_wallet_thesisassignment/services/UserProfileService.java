package com.unipi.christossiap.crypto_wallet_thesisassignment.services;

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

    public UserProfileInfo getAllUserInfo() throws ResourceNotFoundException {
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
    public void editUserProfile(Map<String,String> userInfos) throws ResourceNotFoundException {
        User user = authService.getUser();
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(user.getId());

        userInfos.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    userProfile.setFirstName(value);
                    break;
                case "lastName":
                    userProfile.setLastName(value);
                    break;
                case "phoneNumber":
                    userProfile.setPhoneNumber(value);
                    break;
                case "country":
                    userProfile.setCountry(value);
                    break;
                case "city":
                    userProfile.setCity(value);
                    break;
                case "addressLine":
                    userProfile.setAddressLine(value);
                    break;
                case "postalCode":
                    userProfile.setPostalCode(value);
                    break;
                case "bio":
                    userProfile.setBio(value);
                    break;
                default:
                    break;
            }
        });
    }
}
