package com.github.elivol.accounter.dto.mapper;

import com.github.elivol.accounter.dto.model.UserDto;
import com.github.elivol.accounter.model.user.User;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setFullName(user.getFullName());
    }
}
