package com.github.elivol.accounter.repository;

import com.github.elivol.accounter.model.user.UserRoleModel;
import com.github.elivol.accounter.security.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository underTest;

    @Test
    void canFindByRole() {

        // when
        Optional<UserRoleModel> actual = underTest.findByRole(UserRole.USER);

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getRole()).isEqualTo(UserRole.USER);
    }

    @Test
    void canFindAllRolesIn() {

        // given
        Set<UserRole> roles = Set.of(UserRole.USER, UserRole.ADMIN);

        // when
        Set<UserRoleModel> allRolesIn = underTest.findAllRolesIn(roles);

        // then
        assertThat(allRolesIn).hasSize(roles.size());
        assertThat(allRolesIn).extracting(UserRoleModel::getRole).containsExactlyInAnyOrderElementsOf(roles);
    }
}