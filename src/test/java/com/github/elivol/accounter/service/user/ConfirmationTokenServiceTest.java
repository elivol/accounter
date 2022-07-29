package com.github.elivol.accounter.service.user;

import com.github.elivol.accounter.exception.ConfirmationTokenNotAvailableException;
import com.github.elivol.accounter.exception.ConfirmationTokenNotFoundException;
import com.github.elivol.accounter.model.user.ConfirmationToken;
import com.github.elivol.accounter.model.user.User;
import com.github.elivol.accounter.repository.ConfirmationTokenRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceTest {

    @InjectMocks
    private ConfirmationTokenService underTest;

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    private ConfirmationToken confirmationToken;
    private static User user;
    private static Clock clock;

    @BeforeAll
    static void beforeAll() {
        // setting user
        user = new User(
                1L,
                "test_user",
                "user@test.com",
                "pswd",
                "User");

        // setting clock
        clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @BeforeEach
    void setUp() {
        String token = UUID.randomUUID().toString();

        confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(clock),
                LocalDateTime.now(Clock.offset(clock, Duration.ofHours(16))),
                user);
        confirmationToken.setId(1L);
    }

    @Test
    void canSave() {

        underTest.save(confirmationToken);

        ArgumentCaptor<ConfirmationToken> tokenArgumentCaptor =
                ArgumentCaptor.forClass(ConfirmationToken.class);

        Mockito.verify(confirmationTokenRepository).save(tokenArgumentCaptor.capture());
    }

    @Test
    void canGetTokenEntityByTokenString() {

        String token = confirmationToken.getToken();

        Mockito.when(confirmationTokenRepository.findByToken(token))
                .thenReturn(Optional.of(confirmationToken));

        Optional<ConfirmationToken> actual = underTest.getByToken(token);

        Mockito.verify(confirmationTokenRepository).findByToken(token);
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(confirmationToken);
    }

    @Test
    void canUpdateConfirmationDateTime() {

        String token = confirmationToken.getToken();

        underTest.updateConfirmedAt(token);

        Mockito.verify(confirmationTokenRepository)
                .updateConfirmedAt(Mockito.eq(token), Mockito.any());
    }

    @Test
    void canValidateTokenThatIsValid() {

        String token = confirmationToken.getToken();

        Mockito.when(confirmationTokenRepository.findByToken(token))
                .thenReturn(Optional.of(confirmationToken));

        ConfirmationToken validatedToken = underTest.validateToken(token);

        assertThat(validatedToken).isEqualTo(confirmationToken);
    }

    @Test
    void willThrowExceptionWhenTokenIsNotFound() {

        String token = confirmationToken.getToken();

        Mockito.when(confirmationTokenRepository.findByToken(token))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.validateToken(token))
                .isInstanceOf(ConfirmationTokenNotFoundException.class)
                .hasMessageContainingAll("not found", token);
    }

    @Test
    void willThrowExceptionWhenTokenIsExpired() {

        String token = confirmationToken.getToken();
        confirmationToken.setCreatedAt(LocalDateTime.now(Clock.offset(clock, Duration.ofDays(-1))));
        confirmationToken.setExpiresAt(LocalDateTime.now(Clock.offset(clock, Duration.ofDays(-1))));

        Mockito.when(confirmationTokenRepository.findByToken(token))
                .thenReturn(Optional.of(confirmationToken));

        assertThatThrownBy(() -> underTest.validateToken(token))
                .isInstanceOf(ConfirmationTokenNotAvailableException.class)
                .hasMessageContainingAll("expired", token);
    }

    @Test
    void willThrowExceptionWhenTokenIsAlreadyConfirmed() {

        String token = confirmationToken.getToken();
        confirmationToken.setConfirmedAt(confirmationToken.getExpiresAt().minusMinutes(1));

        Mockito.when(confirmationTokenRepository.findByToken(token))
                .thenReturn(Optional.of(confirmationToken));

        assertThatThrownBy(() -> underTest.validateToken(token))
                .isInstanceOf(ConfirmationTokenNotAvailableException.class)
                .hasMessageContainingAll("confirmed", token);
    }
}