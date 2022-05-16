package com.github.elivol.accounter.security;

public enum UserPermission {
    ACCOUNT_READ("account:read"),
    ACCOUNT_WRITE("account:write"),
    ACCOUNT_OWNER_READ("account_owner:read"),
    ACCOUNT_OWNER_WRITE("account_owner:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
