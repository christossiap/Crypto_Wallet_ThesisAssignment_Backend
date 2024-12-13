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

    @Async
    public void secretSantaEmails(String name, String email) throws MessagingException {
        String text =
                "<h2>🎄 Χο Χο Χο, " + "Ο Santa σε χρειάζεται! 🎅</h2>" +
                        "<p>Η αποστολή σου, αν επιλέξεις να την αποδεχτείς:</p>" +
                        "<p><strong>Ο Χριστουγεννιάτικος Buddy σου για φέτος είναι: </strong> <strong><em style='color:violet;'>" + name + "</em></strong>! 🎁</p>" +
                        "<p>Θυμήσου, το budget είναι αυστηρά <strong>15 ευρώ</strong>. Βρες κάτι ευφάνταστο, αστείο ή συγκινητικό για να κάνεις τη μέρα του ξεχωριστή!</p>" +
                        "<hr>" +
                        "<p style='color:red;'><strong>🎄 Απομένουν μόνο " + 12 + " μέρες μέχρι τα Χριστούγεννα! 🎅</strong></p>" +
                        "<p style='color:green;'>Φύλαξε το μυστικό σου καλά και άσε τη μαγεία των Χριστουγέννων να ζωντανέψει! ❄️</p>" +
                        "<p><strong>Καλά Χριστούγεννα,</strong><br>Η Ομάδα του Άγιου Βασίλη 🎅</p>";

        mailService.sendHtmlEmail(email, "🎅Secret Santa calling....🎁", text);
    }

}