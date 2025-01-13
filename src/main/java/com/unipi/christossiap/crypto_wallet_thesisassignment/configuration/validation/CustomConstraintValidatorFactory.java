//package com.unipi.christossiap.crypto_wallet_thesisassignment.configuration.validation;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
//
//@Component
//public class CustomConstraintValidatorFactory extends SpringConstraintValidatorFactory {
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    public CustomConstraintValidatorFactory(AutowireCapableBeanFactory beanFactory) {
//        super(beanFactory);
//    }
//
//    @Override
//    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
//        return applicationContext.getAutowireCapableBeanFactory().createBean(key);
//    }
//}
//
