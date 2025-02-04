package com.unipi.christossiap.crypto_wallet_thesisassignment.configuration.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import jakarta.validation.Validator;

@Configuration
public class ValidationConfig {

    private final CustomConstraintValidatorFactory customConstraintValidatorFactory;

    public ValidationConfig(CustomConstraintValidatorFactory customConstraintValidatorFactory) {
        this.customConstraintValidatorFactory = customConstraintValidatorFactory;
    }

    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setConstraintValidatorFactory(customConstraintValidatorFactory); // ✅ Register custom factory
        return factoryBean;
    }
}
