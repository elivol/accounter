package com.github.elivol.accounter.model.account;

import com.github.elivol.accounter.admin.currency.AppCurrencyService;
import com.github.elivol.accounter.model.exchangerate.ExchangeRate;
import com.github.elivol.accounter.model.exchangerate.ExchangeRateService;
import com.github.elivol.accounter.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService {

    private static final String ACCOUNT_WITH_ID_NOT_FOUND = "Account with id %d not found";
    private final AccountRepository accountRepository;
    private final AppCurrencyService appCurrencyService;
    private final ExchangeRateService exchangeRateService;

    private ExchangeRate rate(String baseCurrency) {
        return exchangeRateService.rate(baseCurrency);
    }

    public List<Account> findByUser(User owner) {
        return accountRepository.findByUser(owner)
                .stream()
                .peek(a -> {
                    a.setExchangeRate(rate(a.getCurrency().getCurrencyCode()));
                    a.setCurrencyString(a.getCurrency().getCurrencyCode());
                })
                .collect(Collectors.toList());
    }

    public Account findById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(String.format(ACCOUNT_WITH_ID_NOT_FOUND, id))
        );

        account.setCurrencyString(account.getCurrency().getCurrencyCode());
        account.setExchangeRate(rate(account.getCurrency().getCurrencyCode()));
        return account;
    }

    @Transactional
    public Account create(Account account) {
        String currencyString = account.getCurrencyString().toUpperCase();
        boolean isSupported = appCurrencyService.getSupportedCurrencies().stream()
                .anyMatch(c -> c.getCurrencyCode().equalsIgnoreCase(currencyString));

        if (!isSupported){
            throw new IllegalStateException(String.format("Currency %s is not supported", currencyString));
        }

        account.setCurrency(appCurrencyService.findByCurrencyCode(currencyString));
        return accountRepository.save(account);
    }

    @Transactional
    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new NoSuchElementException(String.format(ACCOUNT_WITH_ID_NOT_FOUND, id));
        }
        accountRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Account account) {
        Account existingAccount = this.findById(id);
        existingAccount.setBalance(account.getBalance());
        accountRepository.save(existingAccount);
    }

    @Transactional
    public void updateBalance(Long id, BigDecimal newBalance) {
        Account account = this.findById(id);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

}
