package com.github.elivol.accounter.rest.service;

import com.github.elivol.accounter.entity.AccountOwner;
import com.github.elivol.accounter.rest.repository.AccountOwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountOwnerService {

    private static final String ACCOUNT_OWNER_NOT_FOUND = "Account owner with id %d not found";
    private final AccountOwnerRepository repository;

    public AccountOwnerService(AccountOwnerRepository repository) {
        this.repository = repository;
    }

    public List<AccountOwner> allAccountOwners() {
        return repository.findAll();
    }

    public AccountOwner getAccountOwner(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NoSuchElementException(String.format(ACCOUNT_OWNER_NOT_FOUND, id))
        );
    }

    public void createAccountOwner(AccountOwner accountOwner) {
        repository.save(accountOwner);
    }

    public void deleteAccountOwner(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException(String.format(ACCOUNT_OWNER_NOT_FOUND, id));
        }
        repository.deleteById(id);
    }

    public void updateAccountOwner(Long id, AccountOwner accountOwner) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException(String.format(ACCOUNT_OWNER_NOT_FOUND, id));
        }
        AccountOwner updated = new AccountOwner(
                id,
                accountOwner.getFullName(),
                accountOwner.getContacts()
        );
        repository.save(updated);
    }

}
