package com.github.elivol.accounter.model.account.stats;

import com.github.elivol.accounter.model.HateoasModelRelations;
import com.github.elivol.accounter.model.account.AccountController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountStatsAssembler implements RepresentationModelAssembler<AccountStats, EntityModel<AccountStats>> {
    @Override
    public EntityModel<AccountStats> toModel(AccountStats stats) {
        return EntityModel.of(stats,
                linkTo(methodOn(AccountController.class).stats(null, null, null)).withSelfRel(),
                linkTo(methodOn(AccountController.class).findAll()).withRel(HateoasModelRelations.ACCOUNTS));
    }
}
