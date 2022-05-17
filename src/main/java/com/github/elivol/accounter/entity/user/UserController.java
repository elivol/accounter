package com.github.elivol.accounter.entity.user;

import com.github.elivol.accounter.security.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/me")
    public RepresentationModel<UserProfile> user() {

        User user = AuthenticationService.getCurrentUser();
        Link link = linkTo(methodOn(UserController.class).user()).withSelfRel();
        
        UserProfile userProfile = userService.userProfile(user);
        userProfile.add(link);

        return userProfile;
    }

}
