package com.github.elivol.accounter.hateoas.assembler;

import com.github.elivol.accounter.controller.api.OperationController;
import com.github.elivol.accounter.controller.api.AccountController;
import com.github.elivol.accounter.hateoas.HateoasModelRelations;
import com.github.elivol.accounter.model.Operation;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OperationModelAssembler implements RepresentationModelAssembler<Operation, EntityModel<Operation>> {

    @Override
    public EntityModel<Operation> toModel(Operation operation) {
        return EntityModel.of(
                operation,
                linkTo(methodOn(AccountController.class).findOperationByIdAndAccount(
                        operation.getAccount().getId(),
                        operation.getId())
                ).withSelfRel(),
                linkTo(methodOn(AccountController.class).findAccountOperations(operation.getAccount().getId()))
                        .withRel(HateoasModelRelations.ACCOUNT_OPERATIONS),
                linkTo(methodOn(OperationController.class).findAll()).withRel(HateoasModelRelations.ALL_OPERATIONS)
        );
    }
}
