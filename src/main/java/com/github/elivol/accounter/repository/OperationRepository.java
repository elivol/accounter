package com.github.elivol.accounter.repository;

import com.github.elivol.accounter.model.Account;
import com.github.elivol.accounter.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByAccount(Account account);
    Optional<Operation> findByAccountAndId(Account account, Long id);

    @Query("""
            SELECT o FROM Operation o
            WHERE o.createdAt >= ?2 AND o.createdAt < ?3
            AND o.account = ?1
            """)
    List<Operation> findByAccountAndPeriod(Account account, LocalDateTime from, LocalDateTime to);

}
