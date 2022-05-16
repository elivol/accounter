package com.github.elivol.accounter.registration;

import com.github.elivol.accounter.email.EmailService;
import com.github.elivol.accounter.registration.token.ConfirmationToken;
import com.github.elivol.accounter.registration.token.ConfirmationTokenService;
import com.github.elivol.accounter.user.User;
import com.github.elivol.accounter.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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

    public String register(RegistrationRequest request, String URL) {
        User user = userService.create(
                new User(
                        request.getUsername(),
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

        emailService.send(
                user.getEmail(),
                buildEmail(
                        user.getFullName(),
                        URL + "/confirm?token=" + token,
                        confirmationToken.getExpiresAt()
                )
        );

        return "registered";
    }

    @Transactional
    public String confirm(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.validateToken(token);

        User user = confirmationToken.getUser();
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsCredentialsNonExpired(true);
        user.setIsEnabled(true);

        confirmationTokenService.updateConfirmedAt(token);
        userService.update(user);

        return "confirmed";
    }

    private String buildEmail(String name, String link, LocalDateTime expiresAt) {
        Locale locale = Locale.getDefault();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMM yyyy, HH:mm:ss", locale);

        Context ctx = new Context(locale);
        ctx.setVariable("name", name);
        ctx.setVariable("confirmation_link", link);
        ctx.setVariable("expires_at", expiresAt.format(formatter));

        return templateEngine.process("email/confirm_email", ctx);
    }

}
