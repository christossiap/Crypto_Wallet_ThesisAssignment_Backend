package com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unipi.christossiap.crypto_wallet_thesisassignment.enums.UserStatus;
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
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Username cannot be null")
    @Pattern(regexp = "^[0-9a-zA-Z]{5,20}$|^$", message = "Invalid username (5-20 Latin characters and/or numbers)")
    @UsernameNotExistsConstraint
    private String username;


    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "^[0-9a-zA-Z]{5,20}$", message = "Invalid password (5-20 Latin characters and/or numbers)")
    private String password;

    @NotNull(message="The email must be provided")
    @Email
    @EmailNotExistsConstraint
    private String email;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private String code;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Notification> notifications = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private WatchList watchList;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Portfolio portfolio;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private UserProfile userProfile;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void addWatchList(WatchList watchList) {
        this.setWatchList(watchList);
        watchList.setUser(this);
    }

    public void addPortfolio(Portfolio portfolio) {
        this.setPortfolio(portfolio);
        portfolio.setUser(this);
    }

    public void addUserProfile(UserProfile userProfile) {
        this.setUserProfile(userProfile);
        userProfile.setUser(this);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
        notification.setUser(this);
    }
}