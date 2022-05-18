package com.github.elivol.accounter.entity.account;

import com.github.elivol.accounter.entity.operation.Operation;
import com.github.elivol.accounter.entity.operation.OperationModelAssembler;
import com.github.elivol.accounter.entity.operation.OperationService;
import com.github.elivol.accounter.entity.user.User;
import com.github.elivol.accounter.security.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

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
        return accountModelAssembler.toModel(accountService.findById(id));
    }

    @PostMapping
    public void create(@RequestBody Account account) {
        User user = AuthenticationService.getCurrentUser();
        account.setUser(user);
        accountService.create(account);
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody Account account) {
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
    public void createOperation(@PathVariable Long account_id, @RequestBody Operation operation) {
        operationService.create(account_id, operation);
    }

}
