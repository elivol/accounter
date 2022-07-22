package com.github.elivol.accounter.repository;

import com.github.elivol.accounter.model.AppCurrency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AppCurrencyRepositoryTest {

    @Autowired
    private AppCurrencyRepository underTest;

    @Test
    void canFindByCurrencyCode() {

        // given
        String currencyCode = "USD";
        AppCurrency appCurrency = new AppCurrency(currencyCode);
        underTest.save(appCurrency);

        // when
        Optional<AppCurrency> byCurrencyCode = underTest.findByCurrencyCode(currencyCode);

        // then
        assertThat(byCurrencyCode.isPresent()).isTrue();
        assertThat(byCurrencyCode.get().getCurrencyCode()).isEqualTo(currencyCode);
    }
}