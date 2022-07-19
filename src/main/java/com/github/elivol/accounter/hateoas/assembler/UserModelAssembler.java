package com.github.elivol.accounter.hateoas.assembler;

import com.github.elivol.accounter.controller.api.AccountController;
import com.github.elivol.accounter.controller.api.OperationController;
import com.github.elivol.accounter.controller.api.UserController;
import com.github.elivol.accounter.dto.model.user.UserDto;
import com.github.elivol.accounter.hateoas.HateoasModelRelations;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDto, EntityModel<UserDto>> {

    @Override
    public EntityModel<UserDto> toModel(UserDto user) {
        return EntityModel.of(
                user,
                linkTo(methodOn(UserController.class).user()).withSelfRel(),
                linkTo(methodOn(AccountController.class).findAll()).withRel(HateoasModelRelations.ACCOUNTS),
                linkTo(methodOn(AccountController.class).findById(null)).withRel(HateoasModelRelations.ONE_ACCOUNT),
                linkTo(methodOn(AccountController.class).findAccountOperations(null))
                        .withRel(HateoasModelRelations.ACCOUNT_OPERATIONS),
                linkTo(methodOn(AccountController.class).findOperationByIdAndAccount(null, null))
                        .withRel(HateoasModelRelations.ONE_ACCOUNT_OPERATION),
                linkTo(methodOn(OperationController.class).findAll()).withRel(HateoasModelRelations.ALL_OPERATIONS));
    }
}
