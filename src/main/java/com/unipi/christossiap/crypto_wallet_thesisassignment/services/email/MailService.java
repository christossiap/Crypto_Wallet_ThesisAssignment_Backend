package com.unipi.christossiap.crypto_wallet_thesisassignment.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendTextEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    public void sendHtmlEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        javaMailSender.send(message);
    }

    public void sendHtmlEmailInlineImages(String to, String subject, String body, String[] imagePaths) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setTo(to);
        helper.setSubject(subject);

        helper.setText(body, true);

        for (int i=1; i<=imagePaths.length; i++)
            helper.addInline("image" + i, new ClassPathResource(imagePaths[i-1]));

        javaMailSender.send(message);
    }

    public void sendHtmlEmailWithAttachments(String to, String subject, String body, File[] files) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        for (var file: files)
            helper.addAttachment(file.getName(), file);

        javaMailSender.send(message);
    }
}