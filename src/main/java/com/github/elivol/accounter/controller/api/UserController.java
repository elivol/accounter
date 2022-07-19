package com.github.elivol.accounter.controller.api;

import com.github.elivol.accounter.dto.mapper.UserMapper;
import com.github.elivol.accounter.dto.model.user.UserDto;
import com.github.elivol.accounter.hateoas.assembler.UserDtoModelAssembler;
import com.github.elivol.accounter.service.user.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserDtoModelAssembler userDtoModelAssembler;

    @GetMapping(path = "/me")
    public EntityModel<UserDto> user() {

        UserDto userDto = UserMapper.toUserDto(AuthenticationService.getCurrentUser());
        return userDtoModelAssembler.toModel(userDto);
    }

}
