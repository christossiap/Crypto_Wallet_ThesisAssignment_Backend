package com.unipi.christossiap.crypto_wallet_thesisassignment.models.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailNotExistsValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailNotExistsConstraint {
    String message() default "To email υπάρχει ήδη!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
