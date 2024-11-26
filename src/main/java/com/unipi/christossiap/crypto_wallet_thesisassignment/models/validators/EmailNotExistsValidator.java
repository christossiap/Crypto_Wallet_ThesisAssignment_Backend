package com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators;

import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailNotExistsValidator implements ConstraintValidator<EmailNotExistsConstraint, String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(EmailNotExistsConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findUserByEmail(email)==null;
    }
}
