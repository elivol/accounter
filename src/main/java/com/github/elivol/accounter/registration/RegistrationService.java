package com.github.elivol.accounter.registration;

import com.github.elivol.accounter.email.EmailService;
import com.github.elivol.accounter.registration.token.ConfirmationToken;
import com.github.elivol.accounter.registration.token.ConfirmationTokenService;
import com.github.elivol.accounter.security.user.User;
import com.github.elivol.accounter.security.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;

    public String register(RegistrationRequest request) {
        User user = userService.create(
                new User(
                        request.getEmail(),
                        request.getPassword(),
                        request.getFullName()
                )
        );

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(24),
                user
        );
        confirmationTokenService.save(confirmationToken);

        // TODO: сделать link через builder
        emailService.send(
                user.getEmail(),
                buildEmail(
                        user.getFullName(),
                        "http://localhost:8080/api/v1/register/confirm?token="+token,
                        confirmationToken.getExpiresAt()
                )
        );

        return "registered";
    }

    @Transactional
    public String confirm(String token) {
        confirmationTokenService.validateToken(token);
        confirmationTokenService.updateConfirmedAt(token);
        return "confirmed";
    }

    private String buildEmail(String name, String link, LocalDateTime expiresAt) {
        Locale locale = Locale.getDefault(); // TODO: исправить, брать локаль пользователя, а не сервера
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMM yyyy, HH:mm:ss", locale);

        Context ctx = new Context(locale);
        ctx.setVariable("name", name);
        ctx.setVariable("confirmation_link", link);
        ctx.setVariable("expires_at", expiresAt.format(formatter));

        return templateEngine.process("email/email_confirm", ctx);
    }

}
