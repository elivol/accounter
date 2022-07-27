package com.github.elivol.accounter.repository;

import com.github.elivol.accounter.model.Account;
import com.github.elivol.accounter.model.AppCurrency;
import com.github.elivol.accounter.model.Operation;
import com.github.elivol.accounter.model.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OperationRepositoryTest {

    @Autowired
    private OperationRepository underTest;
    @Autowired
    private AccountRepository accountRepository;

    private List<Operation> operations;

    private static Account account;
    private static User user;
    private static AppCurrency currency;
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

        // setting currency
        currency = new AppCurrency("RUB");
        currency.setId(1L);

        // setting clock
        clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @BeforeEach
    void setUp() {
        account = new Account()
                .setBalance(BigDecimal.valueOf(1500))
                .setCurrency(currency)
                .setUser(user);
        accountRepository.save(account);

        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime afterNow = LocalDateTime.now(Clock.offset(clock, Duration.ofHours(3)));
        BigDecimal amount = BigDecimal.valueOf(500);

        operations = List.of(
                new Operation(now, amount, false, account),
                new Operation(now, amount.add(BigDecimal.valueOf(250)), true, account),
                new Operation(afterNow, amount.subtract(BigDecimal.valueOf(115)), false, account));
        underTest.saveAll(operations);
    }

    @Test
    void canFindByAccount() {

        // when
        List<Operation> actualByAccount = underTest.findByAccount(account);

        // then
        assertThat(actualByAccount).hasSize(operations.size());
        assertThat(actualByAccount).containsExactlyInAnyOrderElementsOf(operations);
        assertThat(actualByAccount).allMatch(o -> o.getAccount().equals(account));
    }

    @Test
    void canFindByAccountAndId() {

        // given
        Operation operation = underTest.save(
                new Operation(
                        LocalDateTime.now(clock),
                        BigDecimal.valueOf(25),
                        false,
                        account));

        // when
        Optional<Operation> actual = underTest.findByAccountAndId(account, operation.getId());

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(operation);
    }

    @Test
    void canFindByAccountAndPeriod() {

        // given
        LocalDateTime from = LocalDateTime.now(Clock.offset(clock, Duration.ofMinutes(-10)));
        LocalDateTime to = LocalDateTime.now(Clock.offset(clock, Duration.ofMinutes(10)));
        List<Operation> expected = operations.subList(0, 2);

        // when
        List<Operation> actualWithPeriod = underTest.findByAccountAndPeriod(account, from, to);

        // then
        assertThat(actualWithPeriod).hasSize(expected.size());
        assertThat(actualWithPeriod).containsExactlyInAnyOrderElementsOf(expected);
    }
}