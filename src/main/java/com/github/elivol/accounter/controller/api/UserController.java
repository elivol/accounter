package com.github.elivol.accounter.controller.api;

import com.github.elivol.accounter.hateoas.HateoasModelRelations;
import com.github.elivol.accounter.model.User;
import com.github.elivol.accounter.dto.UserDto;
import com.github.elivol.accounter.service.user.UserService;
import com.github.elivol.accounter.security.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/me")
    public RepresentationModel<UserDto> user() {

        User user = AuthenticationService.getCurrentUser();

        UserDto userDto = userService.userProfile(user);
        userDto.add(
                linkTo(methodOn(UserController.class).user()).withSelfRel(),
                linkTo(methodOn(AccountController.class).findAll()).withRel(HateoasModelRelations.ACCOUNTS),
                linkTo(methodOn(AccountController.class).findById(null)).withRel(HateoasModelRelations.ONE_ACCOUNT),
                linkTo(methodOn(AccountController.class).findAccountOperations(null))
                        .withRel(HateoasModelRelations.ACCOUNT_OPERATIONS),
                linkTo(methodOn(AccountController.class).findOperationByIdAndAccount(null, null))
                        .withRel(HateoasModelRelations.ONE_ACCOUNT_OPERATION),
                linkTo(methodOn(OperationController.class).findAll()).withRel(HateoasModelRelations.ALL_OPERATIONS)
        );

        return userDto;
    }

}
