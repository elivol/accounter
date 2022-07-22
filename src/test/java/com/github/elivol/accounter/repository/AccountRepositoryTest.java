package com.github.elivol.accounter.repository;

import com.github.elivol.accounter.model.Account;
import com.github.elivol.accounter.model.AppCurrency;
import com.github.elivol.accounter.model.user.User;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository underTest;

    private Account account;
    private static User user;
    private static AppCurrency currency;

    @BeforeAll
    static void beforeAll() {

        // setting user
        user = new User(
                1L,
                "test_user",
                "user@test.com",
                "pswd",
                "User");

        // setting currency
        currency = new AppCurrency("RUB");
        currency.setId(1L);
    }

    @BeforeEach
    void setUp() {
        // setting account
        BigDecimal balance = BigDecimal.valueOf(1500);
        account = new Account()
                .setBalance(balance)
                .setCurrency(currency)
                .setUser(user);
    }

    @Test
    void canGetCurrentBalance() {

        // given
        Account saved = underTest.save(account);

        // when
        Optional<BigDecimal> currentBalance = underTest.getCurrentBalance(saved.getId());

        // then
        assertThat(currentBalance.isPresent()).isTrue();
        assertThat(currentBalance.get()).isCloseTo(saved.getBalance(), Offset.offset(BigDecimal.ZERO));
    }

    @Test
    void canFindByUser() {

        // given
        Account saved = underTest.save(account);

        // when
        List<Account> accounts = underTest.findByUser(user);

        // then
        assertThat(accounts).hasSize(1);
        assertThat(accounts.stream()).allMatch(a -> a.getUser().equals(user));
    }
}