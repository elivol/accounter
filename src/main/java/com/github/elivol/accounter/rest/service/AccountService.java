package com.github.elivol.accounter.rest.service;

import com.github.elivol.accounter.entity.Account;
import com.github.elivol.accounter.rest.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {

    private static final String ACCOUNT_NOT_FOUND = "Account with id %d not found";
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Account findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NoSuchElementException(String.format(ACCOUNT_NOT_FOUND, id))
        );
    }

    public void create(Account account) {
        repository.save(account);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException(String.format(ACCOUNT_NOT_FOUND, id));
        }
        repository.deleteById(id);
    }

    public void update(Long id, Account account) {
        Account updated = new Account(
                id,
                account.getNumber(),
                account.getBalance(),
                account.getOwner()
        );
        repository.save(updated);
    }

}
