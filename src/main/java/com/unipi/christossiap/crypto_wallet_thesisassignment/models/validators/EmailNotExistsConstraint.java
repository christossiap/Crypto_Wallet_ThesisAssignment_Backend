package com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailNotExistsValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailNotExistsConstraint {
    String message() default "Email already exists!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
