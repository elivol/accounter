package com.github.elivol.accounter.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserProfile {

    private String username;
    private String email;
    private String fullName;
}
