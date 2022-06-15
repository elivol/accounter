package com.github.elivol.accounter.model.account;

import com.github.elivol.accounter.model.HateoasModelRelations;
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
                linkTo(methodOn(AccountController.class).stats(null, null, null)).withRel(HateoasModelRelations.ONE_ACCOUNT_STATISTICS));
    }
}
