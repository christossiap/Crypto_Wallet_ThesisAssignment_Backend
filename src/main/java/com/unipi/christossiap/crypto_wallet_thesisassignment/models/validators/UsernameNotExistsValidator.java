package com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators;

import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UsernameNotExistsValidator implements ConstraintValidator<UsernameNotExistsConstraint, String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UsernameNotExistsConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(userRepository.findUserByUsername(username));
        return userRepository.findUserByUsername(username)==null;
    }
}