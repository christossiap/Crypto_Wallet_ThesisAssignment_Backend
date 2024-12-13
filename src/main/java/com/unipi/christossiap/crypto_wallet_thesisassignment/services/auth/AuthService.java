package com.unipi.christossiap.crypto_wallet_thesisassignment.services.auth;

import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserRegisterDTO;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.UserProfile;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.Role;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.RoleRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.UserRepository;
import com.unipi.christossiap.crypto_wallet_thesisassignment.services.email.EmailTemplates;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.AuthException;
import com.unipi.christossiap.crypto_wallet_thesisassignment.DTOs.UserProfileInfo;
import com.unipi.christossiap.crypto_wallet_thesisassignment.settings.exceptions.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
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

    public void saveUser(User user){userRepository.save(user);}

    public User getUser() throws ResourceNotFoundException {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(authentication.getName());
        if (user == null) {throw new ResourceNotFoundException("Δεν βρέθηκε ο χρήστης!");}
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

    @Transactional
    public void registerUser(User user) {
        userRepository.save(user); //για να πάρει ID
        Role role = roleRepository.findRoleByName("USER");
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.addRole(role);
        user.addWatchList(new WatchList());
        Portfolio portfolio = new Portfolio();
        portfolio.setBalance(0.0);
        user.addPortfolio(portfolio);
        user.addUserProfile(new UserProfile());

    // Verification user με το Mail
        user.setStatus("Unverified");
        Random r = new Random();
        user.setCode(String.valueOf(r.nextInt(10000)));
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

    public void changePassword(User user, String password){
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

//    public void userNameReminder(String email){
//        try {
//            emailTemplates.sendEmailUsernameReminder(user);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void deleteUser() throws ResourceNotFoundException {
        User user = getUser();
        userRepository.delete(user);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    public void secretSantaEmails(){
        List<String> names = new ArrayList<>(List.of(
                "Χρήστος", "Θανάσης", "Γεωργία", "Σοφία",
                "Γιάννης", "Γιώτα", "Κατερίνα", "Μήτσος"
        ));

        List<String> mails = new ArrayList<>(List.of(
                "christossiap@hotmail.com", "gthanoulis@gmail.com", "georgia@gmail.com",
                "sofia@gmail.com", "giannis@gmail.com", "giota@gmail.com",
                "katerina@gmail.com", "dimitris@gmail.com"
        ));

        Map<String, String> disallowedEmails = new HashMap<>();
        disallowedEmails.put("Χρήστος", "christossiap@hotmail.com");
        disallowedEmails.put("Θανάσης", "gthanoulis@gmail.com");
        disallowedEmails.put("Γεωργία", "georgia@gmail.com");
        disallowedEmails.put("Σοφία", "sofia@gmail.com");
        disallowedEmails.put("Γιάννης", "giannis@gmail.com");
        disallowedEmails.put("Γιώτα", "giota@gmail.com");
        disallowedEmails.put("Κατερίνα", "katerina@gmail.com");
        disallowedEmails.put("Μήτσος", "dimitris@gmail.com");

        Random r = new Random();
        for (String name : names) {
            String assignedEmail;
            int randomIndex;

            do {
                randomIndex = r.nextInt(mails.size());
                assignedEmail = mails.get(randomIndex);
            } while (disallowedEmails.get(name).equals(assignedEmail));

            try {
                emailTemplates.secretSantaEmails(name, assignedEmail);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            mails.remove(randomIndex);
        }
    }

}