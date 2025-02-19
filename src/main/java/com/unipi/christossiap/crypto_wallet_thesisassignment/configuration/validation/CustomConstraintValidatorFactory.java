package com.unipi.christossiap.crypto_wallet_thesisassignment.configuration.validation;

import jakarta.validation.ConstraintValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

@Component
public class CustomConstraintValidatorFactory extends SpringConstraintValidatorFactory {

    private final ApplicationContext applicationContext;

    public CustomConstraintValidatorFactory(AutowireCapableBeanFactory beanFactory, ApplicationContext applicationContext) {
        super(beanFactory);
        this.applicationContext = applicationContext;
    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        return applicationContext.getAutowireCapableBeanFactory().createBean(key);
    }
}
