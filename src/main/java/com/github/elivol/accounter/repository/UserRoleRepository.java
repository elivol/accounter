package com.github.elivol.accounter.repository;

import com.github.elivol.accounter.security.UserRole;
import com.github.elivol.accounter.model.user.UserRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleModel, Long> {
    Optional<UserRoleModel> findByRole(UserRole role);
}
