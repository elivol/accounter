package com.github.elivol.accounter.controller.api;

import com.github.elivol.accounter.hateoas.assembler.UserDtoModelAssembler;
import com.github.elivol.accounter.model.user.User;
import com.github.elivol.accounter.dto.UserDto;
import com.github.elivol.accounter.service.user.UserService;
import com.github.elivol.accounter.service.user.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoModelAssembler userDtoModelAssembler;

    @GetMapping(path = "/me")
    public EntityModel<UserDto> user() {

        User user = AuthenticationService.getCurrentUser();
        UserDto userDto = UserDto.of(user);
        return userDtoModelAssembler.toModel(userDto);
    }

}
