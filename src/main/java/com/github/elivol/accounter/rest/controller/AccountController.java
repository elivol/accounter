package com.github.elivol.accounter.rest.controller;

import com.github.elivol.accounter.entity.Account;
import com.github.elivol.accounter.rest.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    //@PreAuthorize("hasAuthority('account:read')")
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Account findById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @PostMapping
    public void create(@RequestBody Account account) {
        accountService.create(account);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody Account account) {
        accountService.update(id, account);
    }
}
