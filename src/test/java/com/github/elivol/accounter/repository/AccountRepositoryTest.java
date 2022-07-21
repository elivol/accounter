package com.github.elivol.accounter.repository;

import com.github.elivol.accounter.model.Account;
import com.github.elivol.accounter.model.AppCurrency;
import com.github.elivol.accounter.model.user.User;
import com.github.elivol.accounter.model.user.UserRoleModel;
import com.github.elivol.accounter.security.UserRole;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository underTest;

    private static User user;
    private static AppCurrency currency;

    @BeforeAll
    static void init() {

        // setting user
        user = new User(
                1L,
                "test_user",
                "user@test.com",
                "pswd",
                "User");

        Set<UserRoleModel> userRoles = Set.of(new UserRoleModel(1L, UserRole.USER));
        user.setRoles(userRoles);

        // setting currency
        currency = new AppCurrency("RUB");
        currency.setId(1L);
    }

    @Test
    void canGetCurrentBalance() {

        // given
        BigDecimal balance = BigDecimal.valueOf(1500);

        Account account = new Account()
                .setBalance(balance)
                .setCurrency(currency)
                .setUser(user);

        Account saved = underTest.save(account);

        // when
        Optional<BigDecimal> currentBalance = underTest.getCurrentBalance(saved.getId());

        // then
        assertThat(currentBalance.isPresent()).isTrue();
        assertThat(currentBalance.get()).isCloseTo(balance, Offset.offset(BigDecimal.ZERO));
    }
}