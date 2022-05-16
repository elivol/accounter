package com.github.elivol.accounter.user;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/me")
    @PreAuthorize("isAuthenticated()")
    public UserProfile user(HttpServletRequest request) {
        Principal userPrincipal = request.getUserPrincipal();
        User user = userService.findByUsername(userPrincipal.getName());
        return userService.userProfile(user);
    }

}
