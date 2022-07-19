package com.github.elivol.accounter.dto.model;

import com.github.elivol.accounter.model.user.User;

public class UserDto {

    private String username;
    private String email;
    private String fullName;

    public static UserDto of(User user) {
        return new UserDto()
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setFullName(user.getFullName());
    }

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
