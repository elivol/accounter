package com.github.elivol.accounter.rest.repository;

import com.github.elivol.accounter.entity.AccountOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountOwnerRepository extends JpaRepository<AccountOwner, Long> {
}
