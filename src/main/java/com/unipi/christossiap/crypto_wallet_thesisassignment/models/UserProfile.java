package com.unipi.christossiap.crypto_wallet_thesisassignment.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserProfile {
    @Id
    private Integer id;

    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Pattern(regexp = "^\\+30[0-9]{7,15}$", message = "Phone number must be valid and contain 7 to 15 digits, optionally starting with '+30'")
    private String phoneNumber;

    @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters")
    private String country;

    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    private String city;

    @Size(max = 100, message = "Address line must be at most 100 characters")
    private String addressLine;

    @Pattern(regexp = "^[0-9]{4,10}$", message = "Postal code must be 4 to 10 digits")
    private String postalCode;

    @Size(max = 250, message = "Bio must be at most 250 characters")
    private String bio;

    @JsonBackReference
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
