package com.github.elivol.accounter.hateoas.assembler;

import com.github.elivol.accounter.controller.api.AccountController;
import com.github.elivol.accounter.hateoas.HateoasModelRelations;
import com.github.elivol.accounter.model.Account;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountModelAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {

    @Override
    public EntityModel<Account> toModel(Account account) {
        return EntityModel.of(
                account,
                linkTo(methodOn(AccountController.class).findById(account.getId())).withSelfRel(),
                linkTo(methodOn(AccountController.class).findAll()).withRel(HateoasModelRelations.ACCOUNTS),
                linkTo(methodOn(AccountController.class).findAccountOperations(account.getId())).withRel(HateoasModelRelations.ACCOUNT_OPERATIONS),
                linkTo(methodOn(AccountController.class).findOperationByIdAndAccount(account.getId(), null)).withRel(HateoasModelRelations.ONE_ACCOUNT_OPERATION),
                linkTo(methodOn(AccountController.class).stats(account.getId(), null, null)).withRel(HateoasModelRelations.ONE_ACCOUNT_STATISTICS));
    }
}
