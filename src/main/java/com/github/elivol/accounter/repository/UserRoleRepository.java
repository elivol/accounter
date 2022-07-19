package com.github.elivol.accounter.repository;

import com.github.elivol.accounter.security.UserRole;
import com.github.elivol.accounter.model.user.UserRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleModel, Long> {
    Optional<UserRoleModel> findByRole(UserRole role);

    @Query("""
            SELECT r FROM UserRoleModel r
            WHERE r.role IN ?1
            """)
    Set<UserRoleModel> findAllRolesIn(Set<UserRole> roles);
}
