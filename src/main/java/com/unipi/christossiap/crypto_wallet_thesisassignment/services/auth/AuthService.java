package com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.authDTOs.RegisterRequest;
import com.unipi.christossiap.crypto_wallet_thesisassignment.enums.UserStatus;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.UserProfile;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.Role;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.RoleRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.UserRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.email.EmailTemplates;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.AuthException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.CodeNotMatchingException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public EmailTemplates emailTemplates;
    public void saveUser(User user){userRepository.save(user);}

    public User getUser(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(authentication.getName());
        if (user == null) {throw new AuthException("User not found!");}
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        else if (user.getStatus() == UserStatus.UNVERIFIED) {
            throw new AuthException("Unverified user. Login denied!");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(userRepository.findUserRoles(user.getId()))
        );
    }

    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        User user = new User();
        Role role = roleRepository.findRoleByName("USER");
        user.addRole(role);
        user.setUsername(registerRequest.username());
        String encodedPassword = passwordEncoder.encode(registerRequest.password());
        user.setPassword(encodedPassword);
        user.setEmail(registerRequest.email());

        user.addWatchList(new WatchList());

        Portfolio portfolio = new Portfolio();
        portfolio.setBalance(0.0);
        user.addPortfolio(portfolio);

        user.addUserProfile(new UserProfile());

        user.setStatus(UserStatus.UNVERIFIED);

        user.setCode(UUID.randomUUID().toString());
        userRepository.save(user);

//         Uncomment if email functionality is implemented
         try {
             emailTemplates.sendEmailCompleteRegister(user);
         } catch (MessagingException e) {
             throw new RuntimeException(e);
         }
    }

    public void registerComplete(String code) {
        User user = userRepository.findUserByCode(code);
        if (user == null) throw new CodeNotMatchingException("Invalid or expired verification code");
        user.setStatus(UserStatus.VERIFIED);
        user.setCode(null);
        userRepository.save(user);
    }

    @Transactional
    public void changePassword(String password) { //When logged in
        User user = getUser();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Transactional
    public void changeUsername(String username) {
        User user = getUser();
        user.setUsername(username);
        userRepository.save(user);
    }

    @Transactional
    public void emailChange(String email) {
        User user = getUser();
        user.setEmail(email);
        userRepository.save(user);
    }


    @Transactional
    public void userNameReminder(String email){
        User user = userRepository.findUserByEmail(email);
        if (user == null){throw new AuthException("User not found with this registered email!");}
        user.setCode(UUID.randomUUID().toString());
        saveUser(user);
        try {
            emailTemplates.sendEmailUsernameReminder(user);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        log.info(user.getUsername()); //monitoring result.
    }

    @Transactional
    public void passwordReminder(String email){
        User user = userRepository.findUserByEmail(email);
        if (user == null){throw new AuthException("User not found with this registered email!");}
        user.setCode(UUID.randomUUID().toString());
        userRepository.save(user);
        try {
            emailTemplates.sendEmailResetPassword(user);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void passwordResetConfirmation(String username, String code, String newPassword){
        User user = userRepository.findUserByUsername(username);
        if (user == null){throw new AuthException("User not found with this registered username!");}
        if (user.getCode().equals(code)){
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            user.setCode(null);
            saveUser(user);
        }else {throw new CodeNotMatchingException("Invalid confirmation code..");}
    }

    @Transactional
    public void deleteUser() throws ResourceNotFoundException {
        User user = getUser();
        userRepository.delete(user);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

}