package com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.UserProfile;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.Role;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.RoleRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.UserRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.email.EmailTemplates;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.AuthException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserInfo;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public EmailTemplates emailTemplates;


    public User getUser(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByUsername(authentication.getName());
    }
    public List<User> getAllUsers(){return userRepository.findAll();}
    public List<UserInfo> getAllUserDetails(){return userRepository.findUserInfo();}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        else if (user.getStatus().equals("unverified")) {
            throw new AuthException("Unverified user. Login denied!");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(userRepository.findUserRoles(user.getId()))
        );
    }

    public void registerUser(User user) {
        Role role = roleRepository.findRoleByName("USER");
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.addRole(role);

    // Verification user με το Mail
        user.setStatus("Unverified");
        Random r = new Random();
        user.setCode(String.valueOf(r.nextInt(1000)));
        // Handle associations with WatchList, Portfolio, and UserProfile
        // Create new WatchList, Portfolio, and UserProfile if they are null (or just set if they already exist)
        WatchList watchList = new WatchList();
        Portfolio portfolio = new Portfolio();
        UserProfile userProfile = new UserProfile();

        // Associate these with the user
        user.setWatchList(watchList);
        user.setPortfolio(portfolio);
        user.setUserProfile(userProfile);

        // Set the reverse side of the relationships to ensure bidirectional consistency
        watchList.setUser(user);
        portfolio.setUser(user);
        userProfile.setUser(user);

        // Save the user, which will cascade the changes to WatchList, Portfolio, and UserProfile
        userRepository.save(user);
//        try {
//            emailTemplates.sendEmailCompleteRegister(user);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
    }

    public Boolean registerComplete(String code){
        User user = userRepository.findUserByCode(code);
        if (user==null)
            return false;

        user.setStatus("verified");
        userRepository.save(user);
        return true;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}