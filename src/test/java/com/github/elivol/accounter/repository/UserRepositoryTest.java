package com.github.elivol.accounter.repository;

import com.github.elivol.accounter.model.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    private static String username;
    private static String email;

    @BeforeAll
    static void beforeAll() {
        username = "test_user";
        email = "user@test.com";
    }

    @Test
    void canFindByUsername() {

        // when
        Optional<User> byUsername = underTest.findByUsername(username);

        // then
        assertThat(byUsername.isPresent()).isTrue();
        assertThat(byUsername.get().getUsername()).isEqualTo(username);
    }

    @Test
    void canFindByEmail() {

        // when
        Optional<User> byEmail = underTest.findByEmail(email);

        // then
        assertThat(byEmail.isPresent()).isTrue();
        assertThat(byEmail.get().getEmail()).isEqualTo(email);
    }
}