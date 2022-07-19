package com.github.elivol.accounter.dto.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class UserDto {

    private String username;
    private String email;
    private String fullName;
}
