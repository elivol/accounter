package com.github.elivol.accounter.service.user;

import com.github.elivol.accounter.exception.ConfirmationTokenNotAvailableException;
import com.github.elivol.accounter.exception.ConfirmationTokenNotFoundException;
import com.github.elivol.accounter.model.user.ConfirmationToken;
import com.github.elivol.accounter.repository.ConfirmationTokenRepository;
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

    public void updateConfirmedAt(String token) {
        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public ConfirmationToken validateToken(String token) {
        ConfirmationToken confirmationToken = this.getByToken(token)
                .orElseThrow(() ->
                        new ConfirmationTokenNotFoundException(String.format(TOKEN_NOT_FOUND, token)));

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenNotAvailableException(String.format(TOKEN_EXPIRED, token));
        }

        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmationTokenNotAvailableException(String.format(TOKEN_CONFIRMED, token));
        }

        return confirmationToken;
    }
}
