package com.github.elivol.accounter.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RegistrationRequest {

    private String email;
    private String password;
    private String fullName;

}
