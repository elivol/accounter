package com.github.elivol.accounter.admin.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppCurrencyRepository extends JpaRepository<AppCurrency, Long> {

    @Query("""
            SELECT c FROM AppCurrency c
            WHERE c.currencyCode = ?1
            """)
    Optional<AppCurrency> findByCurrencyCode(String currencyCode);
}
