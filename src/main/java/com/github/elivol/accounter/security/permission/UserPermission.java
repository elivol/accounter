package com.github.elivol.accounter.security.permission;

public enum UserPermission {
    ACCOUNT_READ("account:read"),
    ACCOUNT_WRITE("account:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
