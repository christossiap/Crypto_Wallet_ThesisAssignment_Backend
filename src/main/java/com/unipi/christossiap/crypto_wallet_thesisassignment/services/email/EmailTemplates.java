package com.unipi.christossiap.crypto_wallet_thesisassignment.services.email;

import com.unipi.christossiap.crypto_wallet_thesisassignment.models.auth.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplates {

    @Autowired
    private MailService mailService;


    @Async
    public void sendEmailCompleteRegister(User user) throws MessagingException {
        String text =
                "O κωδικός είναι: " + user.getCode();

        mailService.sendHtmlEmail(user.getEmail(), "Eπαλήθευση Εγγραφής", text);
    }

    @Async
    public void sendEmailUsernameReminder(User user) throws MessagingException {
        String text =
                "Το username είναι: " + user.getUsername();

        mailService.sendHtmlEmail(user.getEmail(), "Υπενθύμιση Username", text);
    }

    @Async
    public void sendEmailResetPassword(User user) throws MessagingException {
        String text =
                "O κωδικός για αλλαγή του password είναι: " + user.getCode();

        text += " (αν δεν κάνατε εσείς το αίτημα αγνοήστε αυτό το μήνυμα)";

        mailService.sendHtmlEmail(user.getEmail(), "Password Reset", text);
    }
}