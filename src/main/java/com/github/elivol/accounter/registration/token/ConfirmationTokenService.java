package com.github.elivol.accounter.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private static final String TOKEN_NOT_FOUND = "Confirmation token %s not found";
    private static final String TOKEN_EXPIRED = "Token %s has been expired";
    private static final String TOKEN_CONFIRMED = "Token %s is already confirmed";
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void save(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getByToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int updateConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public ConfirmationToken validateToken(String token) {
        ConfirmationToken confirmationToken = this.getByToken(token)
                .orElseThrow(() ->
                        new IllegalStateException(String.format(TOKEN_NOT_FOUND, token)));

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException(String.format(TOKEN_EXPIRED, token));
        }

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException(String.format(TOKEN_CONFIRMED, token));
        }

        return confirmationToken;
    }

}
