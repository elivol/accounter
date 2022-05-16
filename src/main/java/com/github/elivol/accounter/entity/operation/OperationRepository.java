package com.github.elivol.accounter.entity.operation;

import com.github.elivol.accounter.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByAccount(Account account);

    Optional<Operation> findByAccountAndId(Account account, Long id);

}
