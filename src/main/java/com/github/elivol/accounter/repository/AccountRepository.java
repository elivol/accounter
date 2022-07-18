package com.github.elivol.accounter.repository;

import com.github.elivol.accounter.model.Account;
import com.github.elivol.accounter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);

    @Query("""
            SELECT a.balance
            FROM Account a
            WHERE a.id = ?1
            """)
    Optional<BigDecimal> getCurrentBalance(Long id);

}
