package com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Notification;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.Portfolio;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.UserProfile;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.WatchList;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators.EmailNotExistsConstraint;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators.UsernameNotExistsConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @Pattern(regexp = "^[0-9a-zA-Z]{5,20}$|^$", message = "Μη έγκυρο username (5-20 λατινικοί χαρακτήρες ή/και αριθμοί)")
//    @UsernameNotExistsConstraint(message = "Το όνομα χρήστη υπάρχει ήδη. Επιλέξτε νέο!")
    private String username;

//    @Pattern(regexp = "^[0-9a-zA-Z]{5,20}$|^$", message = "Μη έγκυρο password (5-20 λατινικοί χαρακτήρες ή/και αριθμοί)")
    private String password;

//    @NotNull(message="Το email πρέπει να είναι συμπληρωμένο")
    @Email
//    @EmailNotExistsConstraint(message = "Το e-mail που δώσατε χρησιμοποιείται από άλλον χρήστη. Επιλέξτε νέο!")
    private String email;

    private String status;
    private String code;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notifications = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private WatchList watchList;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Portfolio portfolio;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private UserProfile userProfile;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "UserRole",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")

    )
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role) {
        roles.add(role);
    }
}
