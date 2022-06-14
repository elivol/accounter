package com.github.elivol.accounter.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
public class UserProfile extends RepresentationModel<UserProfile> {

    private String username;
    private String email;
    private String fullName;
}
