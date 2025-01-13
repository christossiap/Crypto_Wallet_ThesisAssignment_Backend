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
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.UserValidationExceptions;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
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

import java.net.BindException;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public void validateUser(String username,String email) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (userRepository.existsByUsername(username)) {
            throw new UserValidationExceptions("Username already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new UserValidationExceptions("Email already exists");
        }
    }

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
        else if (user.getStatus().equals("unverified")) {
            throw new AuthException("Unverified user. Login denied!");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(userRepository.findUserRoles(user.getId()))
        );
    }

//    @Transactional
//    public void registerUser(User user) {
//        userRepository.save(user); //για να πάρει ID
//        Role role = roleRepository.findRoleByName("USER");
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
//        user.addRole(role);
//        user.addWatchList(new WatchList());
//        Portfolio portfolio = new Portfolio();
//        portfolio.setBalance(0.0);
//        user.addPortfolio(portfolio);
//        user.addUserProfile(new UserProfile());
//
//    // Verification user με το Mail
//        user.setStatus("Unverified");
//        Random r = new Random();
//        user.setCode(String.valueOf(r.nextInt(10000)));
//        userRepository.save(user);
////        try {
////            emailTemplates.sendEmailCompleteRegister(user);
////        } catch (MessagingException e) {
////            throw new RuntimeException(e);
////        }
//    }

    @Transactional
    public void registerUser(User user) {
        validateUser(user.getUsername(),user.getEmail());
        Role role = roleRepository.findRoleByName("USER");
        user.addRole(role);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.addWatchList(new WatchList());

        Portfolio portfolio = new Portfolio();
        portfolio.setBalance(0.0);
        user.addPortfolio(portfolio);

        user.addUserProfile(new UserProfile());

        user.setStatus("Unverified");
        Random random = new Random();
        user.setCode(String.valueOf(random.nextInt(10000)));

        userRepository.save(user);

        // Uncomment if email functionality is implemented
//         try {
//             emailTemplates.sendEmailCompleteRegister(user);
//         } catch (MessagingException e) {
//             throw new RuntimeException(e);
//         }
    }


    public Boolean registerComplete(String code){
        User user = userRepository.findUserByCode(code);
        if (user==null)
            return false;

        user.setStatus("verified");
        userRepository.save(user);
        return true;
    }

    public void changePassword(User user, String password){
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public User userNameReminder(String email){
        User user = userRepository.findUserByEmail(email);
        if (user == null){throw new AuthException("User not found with this registered email!");}
        Random r = new Random();
        user.setCode(String.valueOf(r.nextInt(10000)));
        saveUser(user);
        try {
            emailTemplates.sendEmailUsernameReminder(user);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void passwordReminder(String username){
        User user = userRepository.findUserByUsername(username);
        if (user == null){throw new AuthException("User not found with this registered username!");}
        try {
            emailTemplates.sendEmailResetPassword(user);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void passwordResetConfirmation(String username, String code, String newPassword){
        User user = userRepository.findUserByUsername(username);
        if (user.getCode().equals(code)){
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            saveUser(user);
        }else {throw new RuntimeException("Invalid confirmation code..");}
    }

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