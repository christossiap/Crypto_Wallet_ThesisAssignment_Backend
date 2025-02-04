
package com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators;

import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UsernameNotExistsValidator implements ConstraintValidator<UsernameNotExistsConstraint, String> {

    private final UserRepository userRepository;

    public UsernameNotExistsValidator(UserRepository userRepository) {
        System.out.println("UsernameNotExistsValidator instantiated");
        this.userRepository = userRepository;

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        boolean exists = userRepository.existsByUsername(username);
        //System.out.println(userRepository.existsByUsername(username));
        return !exists;
    }
}
