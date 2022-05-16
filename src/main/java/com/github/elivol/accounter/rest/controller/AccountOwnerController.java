package com.github.elivol.accounter.rest.controller;

import com.github.elivol.accounter.entity.AccountOwner;
import com.github.elivol.accounter.rest.service.AccountOwnerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account_owners")
public class AccountOwnerController {

    private final AccountOwnerService accountOwnerService;

    public AccountOwnerController(AccountOwnerService accountOwnerService) {
        this.accountOwnerService = accountOwnerService;
    }

    @GetMapping
    public List<AccountOwner> findAll() {
        return accountOwnerService.findAll();
    }

    @GetMapping(path = "/{id}")
    public AccountOwner findById(@PathVariable Long id) {
        return accountOwnerService.findById(id);
    }

    @PostMapping
    public void create(@RequestBody AccountOwner accountOwner) {
        accountOwnerService.create(accountOwner);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        accountOwnerService.delete(id);
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody AccountOwner accountOwner) {
        accountOwnerService.update(id, accountOwner);
    }


}
