package com.github.elivol.accounter.model.account;

import com.github.elivol.accounter.model.account.stats.AccountStats;
import com.github.elivol.accounter.model.account.stats.AccountStatsAssembler;
import com.github.elivol.accounter.model.account.stats.AccountStatsService;
import com.github.elivol.accounter.model.operation.Operation;
import com.github.elivol.accounter.model.operation.OperationModelAssembler;
import com.github.elivol.accounter.model.operation.OperationService;
import com.github.elivol.accounter.model.user.User;
import com.github.elivol.accounter.security.AuthenticationService;
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
    private final AccountStatsAssembler accountStatsAssembler;


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

    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody Account account) {
        User user = AuthenticationService.getCurrentUser();
        account.setUser(user);
        accountService.update(id, account);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }


    /*
    * Actions with operations of specific account
    * */

    @GetMapping(path = "/{account_id}/operations")
    public CollectionModel<EntityModel<Operation>> findAccountOperations(@PathVariable Long account_id) {

        List<EntityModel<Operation>> operations = operationService.findAccountOperations(account_id)
                .stream()
                .map(operationModelAssembler::toModel)
                .toList();

        return CollectionModel.of(
                operations,
                linkTo(methodOn(AccountController.class).findAccountOperations(account_id)).withSelfRel()
        );
    }

    @GetMapping(path = "/{account_id}/operations/{id}")
    public EntityModel<Operation> findOperationByIdAndAccount(@PathVariable Long account_id, @PathVariable Long id) {
        return operationModelAssembler.toModel(operationService.findByIdAndAccount(account_id, id));
    }

    @PostMapping(path = "/{account_id}/operations")
    public void createOperation(@PathVariable Long account_id, @Valid @RequestBody Operation operation) {
        operationService.create(account_id, operation);
    }


    /*
    * Actions with account statistics
    * */
    @GetMapping(path = "/{account_id}/stats")
    public EntityModel<AccountStats> stats(@PathVariable Long account_id,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        AccountStats stats = accountStatsService.stats(account_id, from, to);
        return accountStatsAssembler.toModel(stats);
    }

}
