package com.github.elivol.accounter.dto.model.user;

import com.github.elivol.accounter.model.user.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserRegistrationRequest {

    @Pattern(regexp = "^[\\w_]{2,15}$", message = "must be from 2 to 15 symbols (digits, characters, underscore)")
    private String username;

    @NotEmpty
    @Email
    private String email;

    @Size(min = 5, max = 20)
    private String password;

    @Size(min = 2, max = 300)
    private String fullName;
}
