package com.github.elivol.accounter.entity.account;

import com.github.elivol.accounter.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AccountService {

    private static final String ACCOUNT_WITH_ID_NOT_FOUND = "Account with id %d not found";
    private final AccountRepository accountRepository;

    public List<Account> findByUser(User owner) {
        return accountRepository.findByUser(owner);
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(String.format(ACCOUNT_WITH_ID_NOT_FOUND, id))
        );
    }

    public Account create(Account account) {
        return accountRepository.save(account);
    }

    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new NoSuchElementException(String.format(ACCOUNT_WITH_ID_NOT_FOUND, id));
        }
        accountRepository.deleteById(id);
    }

    public void update(Long id, Account account) {
        Account existingAccount = this.findById(id);
        existingAccount.setBalance(account.getBalance());
        accountRepository.save(existingAccount);
    }

    public void updateBalance(Long id, BigDecimal newBalance) {
        Account account = this.findById(id);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

}
