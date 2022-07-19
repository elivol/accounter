package com.github.elivol.accounter.controller.api;

import com.github.elivol.accounter.model.Account;
import com.github.elivol.accounter.hateoas.assembler.AccountModelAssembler;
import com.github.elivol.accounter.dto.model.AccountStats;
import com.github.elivol.accounter.hateoas.assembler.AccountStatsModelAssembler;
import com.github.elivol.accounter.service.AccountStatsService;
import com.github.elivol.accounter.model.Operation;
import com.github.elivol.accounter.hateoas.assembler.OperationModelAssembler;
import com.github.elivol.accounter.service.OperationService;
import com.github.elivol.accounter.model.user.User;
import com.github.elivol.accounter.service.user.AuthenticationService;
import com.github.elivol.accounter.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "me/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final OperationService operationService;
    private final AccountModelAssembler accountModelAssembler;
    private final OperationModelAssembler operationModelAssembler;
    private final AccountStatsService accountStatsService;
    private final AccountStatsModelAssembler accountStatsModelAssembler;


    /*
    * Actions with accounts
    * */
    @GetMapping
    public CollectionModel<EntityModel<Account>> findAll() {
        User user = AuthenticationService.getCurrentUser();

        List<EntityModel<Account>> accounts = accountService.findByUser(user)
                .stream()
                .map(accountModelAssembler::toModel)
                .toList();

        return CollectionModel.of(accounts, linkTo(methodOn(AccountController.class).findAll()).withSelfRel());
    }

    @GetMapping(path = "/{id}")
    public EntityModel<Account> findById(@PathVariable Long id) {
        Account account = accountService.findById(id);
        return accountModelAssembler.toModel(account);
    }

    @PostMapping
    public void create(@Valid @RequestBody Account account) {
        User user = AuthenticationService.getCurrentUser();
        account.setUser(user);
        accountService.create(account);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }


    /*
    * Actions with operations of specific account
    * */

    @GetMapping(path = "/{account_id}/operations")
    public CollectionModel<EntityModel<Operation>> findAccountOperations(@PathVariable("account_id") Long accountId) {

        List<EntityModel<Operation>> operations = operationService.findAccountOperations(accountId)
                .stream()
                .map(operationModelAssembler::toModel)
                .toList();

        return CollectionModel.of(
                operations,
                linkTo(methodOn(AccountController.class).findAccountOperations(accountId)).withSelfRel()
        );
    }

    @GetMapping(path = "/{account_id}/operations/{id}")
    public EntityModel<Operation> findOperationByIdAndAccount(
            @PathVariable("account_id") Long accountId,
            @PathVariable Long id) {
        return operationModelAssembler.toModel(operationService.findByIdAndAccount(accountId, id));
    }

    @PostMapping(path = "/{account_id}/operations")
    public void createOperation(@PathVariable("account_id") Long accountId, @Valid @RequestBody Operation operation) {
        operationService.create(accountId, operation);
    }


    /*
    * Actions with account statistics
    * */
    @GetMapping(path = "/{account_id}/stats")
    public EntityModel<AccountStats> stats(@PathVariable("account_id") Long accountId,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        AccountStats stats = accountStatsService.stats(accountId, from, to);
        return accountStatsModelAssembler.toModel(stats);
    }

}
