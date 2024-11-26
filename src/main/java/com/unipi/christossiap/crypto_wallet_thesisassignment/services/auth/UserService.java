package com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth;


import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserDetails;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.Role;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.RoleRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User getUser(String username){
        return userRepository.findUserByUsername(username);
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<UserDetails> getAllUserDetails(){return userRepository.findUserDetails();}
}
