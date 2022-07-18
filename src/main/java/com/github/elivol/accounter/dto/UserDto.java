package com.github.elivol.accounter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
public class UserDto extends RepresentationModel<UserDto> {

    private String username;
    private String email;
    private String fullName;
}
