package com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators;

import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public class EmailNotExistsValidator implements ConstraintValidator<EmailNotExistsConstraint, String> {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmailNotExistsValidator.class);

    @Override
    public void initialize(EmailNotExistsConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true; // Skip validation for null/empty values.
        }
        return !userRepository.existsByEmail(email);
    }


}
