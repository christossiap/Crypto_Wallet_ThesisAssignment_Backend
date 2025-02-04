package com.unipi.christossiap.crypto_wallet_thesisassignment.services.email;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import com.unipi.christossiap.crypto_wallet_thesisassignment.repositories.auth.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class EmailTemplates {

    @Autowired
    private MailService mailService;


    @Async
    public void sendEmailCompleteRegister(User user) throws MessagingException {
        String text =
                "The code is: " + user.getCode();

        mailService.sendHtmlEmail(user.getEmail(), "Registration Verification", text);
    }

    @Async
    public void sendEmailUsernameReminder(User user) throws MessagingException {
        String text =
                "The username is: " + user.getUsername();

        mailService.sendHtmlEmail(user.getEmail(), "Username Reminder", text);
    }

    @Async
    public void sendEmailResetPassword(User user) throws MessagingException {

        String text =
                "The code for password reset is: " + user.getCode();

        text += " (If you did not request this, please ignore this message)";

        mailService.sendHtmlEmail(user.getEmail(), "Password Reset", text);
    }

}