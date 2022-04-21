package com.github.elivol.accounter.security.permission;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.github.elivol.accounter.security.permission.UserPermission.ACCOUNT_READ;
import static com.github.elivol.accounter.security.permission.UserPermission.ACCOUNT_WRITE;

public enum UserRole {
    ADMIN(Set.of(ACCOUNT_WRITE)),
    USER(Set.of(ACCOUNT_READ, ACCOUNT_WRITE));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
