package com.github.elivol.accounter.entity.account;

import com.github.elivol.accounter.entity.operation.Operation;
import com.github.elivol.accounter.entity.operation.OperationService;
import com.github.elivol.accounter.security.AuthenticationService;
import com.github.elivol.accounter.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "me/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final OperationService operationService;


    /*
    * Actions with accounts
    * */

    @GetMapping
    public List<Account> findAll() {
        User user = AuthenticationService.getCurrentUser();
        return accountService.findByUser(user);
    }

    @GetMapping(path = "/{id}")
    public Account findById(@PathVariable Long id) {
        return accountService.findById(id);
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
    public List<Operation> findAccountOperations(@PathVariable Long account_id) {
        return operationService.findAccountOperations(account_id);
    }

    @GetMapping(path = "/{account_id}/operations/{id}")
    public Operation findOperationByIdAndAccount(@PathVariable Long account_id, @PathVariable Long id) {
        return operationService.findByIdAndAccount(account_id, id);
    }

    @PostMapping(path = "/{account_id}/operations")
    public void createOperation(@PathVariable Long account_id, @RequestBody Operation operation) {
        operationService.create(account_id, operation);
    }

}
