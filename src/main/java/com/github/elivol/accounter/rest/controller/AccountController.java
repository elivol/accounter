package com.github.elivol.accounter.rest.controller;

import com.github.elivol.accounter.entity.Account;
import com.github.elivol.accounter.entity.AccountOwner;
import com.github.elivol.accounter.rest.service.AccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    //@PreAuthorize("hasAuthority('account:read')")
    public List<Account> allAccounts() {
        return service.allAccounts();
    }

    @GetMapping(path = "/{id}")
    public Account getAccount(@PathVariable Long id) {
        return service.getAccount(id);
    }

    @PostMapping
    public void createAccount(@RequestBody Account account) {
        service.createAccount(account);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
    }

    @PutMapping(path = "/{id}")
    public void updateAccount(@PathVariable Long id, @RequestBody Account account) {
        service.updateAccount(id, account);
    }
}
