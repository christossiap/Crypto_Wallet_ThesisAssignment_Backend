package com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators;

import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailNotExistsValidator implements ConstraintValidator<EmailNotExistsConstraint, String> {

    private final UserRepository userRepository;

    public EmailNotExistsValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        try {
            boolean exists = userRepository.existsByEmail(email);
            return !exists;
        } catch (Exception e) {
            System.out.println("Error checking email: " + e.getMessage());
            return false;
        }
    }

}

