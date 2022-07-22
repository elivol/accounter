package com.github.elivol.accounter.repository;

import com.github.elivol.accounter.model.user.ConfirmationToken;
import com.github.elivol.accounter.model.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ConfirmationTokenRepositoryTest {

    @Autowired
    private ConfirmationTokenRepository underTest;

    private static User user;
    private Long id;
    private String token;

    @BeforeAll
    static void beforeAll() {
        // setting user
        user = new User(
                1L,
                "test_user",
                "user@test.com",
                "pswd",
                "User");
    }

    @BeforeEach
    void setUp() {
        token = UUID.randomUUID().toString();
        ConfirmationToken tokenEntity = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                user);
        underTest.save(tokenEntity);
        id = tokenEntity.getId();
    }

    @Test
    void canFindByToken() {

        // when
        Optional<ConfirmationToken> byToken = underTest.findByToken(token);

        // then
        assertThat(byToken.isPresent()).isTrue();
        assertThat(byToken.get().getToken()).isEqualTo(token);
    }

    @Test
    void canUpdateConfirmedAt() {

        // given
        LocalDateTime confirmedAt = LocalDateTime.now().plusMinutes(5);

        // when
        int count = underTest.updateConfirmedAt(token, confirmedAt);
        Optional<ConfirmationToken> updated = underTest.findById(id);

        // then
        assertThat(count).isEqualTo(1);
        assertThat(updated.isPresent()).isTrue();
        assertThat(updated.get().getId()).isEqualTo(id);
        assertThat(updated.get().getConfirmedAt()).isEqualToIgnoringNanos(confirmedAt);
    }
}