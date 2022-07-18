package com.github.elivol.accounter.service.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@EnableAsync
public class EmailService implements EmailSender {

    private final JavaMailSender javaMailSender;
    @Value("${email-confirm.from}")
    private String from;
    @Value("${email-confirm.subject}")
    private String subject;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(email, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException(e);
        }
    }
}
