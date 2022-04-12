package com.github.elivol.accounter.rest.controller;

import com.github.elivol.accounter.entity.AccountOwner;
import com.github.elivol.accounter.rest.service.AccountOwnerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account_owners")
public class AccountOwnerController {

    private final AccountOwnerService service;

    public AccountOwnerController(AccountOwnerService service) {
        this.service = service;
    }

    @GetMapping
    public List<AccountOwner> allAccountOwners() {
        return service.allAccountOwners();
    }

    @GetMapping(path = "/{id}")
    public AccountOwner getAccountOwner(@PathVariable Long id) {
        return service.getAccountOwner(id);
    }

    @PostMapping
    public void createAccountOwner(@RequestBody AccountOwner accountOwner) {
        service.createAccountOwner(accountOwner);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteAccountOwner(@PathVariable Long id) {
        service.deleteAccountOwner(id);
    }

    @PutMapping(path = "/{id}")
    public void updateAccountOwner(@PathVariable Long id, @RequestBody AccountOwner accountOwner) {
        service.updateAccountOwner(id, accountOwner);
    }


}
