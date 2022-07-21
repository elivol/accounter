package com.github.elivol.accounter.service;

import com.github.elivol.accounter.dto.mapper.AccountMapper;
import com.github.elivol.accounter.dto.model.AccountDto;
import com.github.elivol.accounter.exception.CurrencyIsNotSupportedException;
import com.github.elivol.accounter.exception.EntityNotFoundException;
import com.github.elivol.accounter.exception.ExchangeRateException;
import com.github.elivol.accounter.model.Account;
import com.github.elivol.accounter.model.AppCurrency;
import com.github.elivol.accounter.repository.AccountRepository;
import com.github.elivol.accounter.dto.model.exchangerate.ExchangeRate;
import com.github.elivol.accounter.dto.model.exchangerate.ExchangeRateErrorResponse;
import com.github.elivol.accounter.model.user.User;
import com.github.elivol.accounter.service.user.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService {

    private static final String ACCOUNT_WITH_ID_NOT_FOUND = "Account with id %d not found";
    public static final String CURRENCY_IS_NOT_SUPPORTED = "Currency %s is not supported";
    private final AccountRepository accountRepository;
    private final AppCurrencyService appCurrencyService;
    private final ExchangeRateService exchangeRateService;

    private ExchangeRate rate(String baseCurrency) {
        try {
            return exchangeRateService.rate(baseCurrency);
        } catch (ExchangeRateException ex) {
            return new ExchangeRateErrorResponse(ex.getMessage(), ex.getErrorType());
        }
    }

    public List<Account> findByUser(User owner) {
        return accountRepository.findByUser(owner)
                .stream()
                .peek(a -> {
                    a.setExchangeRate(rate(a.getCurrency().getCurrencyCode()));
                })
                .collect(Collectors.toList());
    }

    public Account findById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(ACCOUNT_WITH_ID_NOT_FOUND, id))
        );

        account.setExchangeRate(rate(account.getCurrency().getCurrencyCode()));
        return account;
    }

    public BigDecimal getCurrentBalance(Long id) {
        return accountRepository.getCurrentBalance(id).orElse(BigDecimal.ZERO);
    }

    @Transactional
    public Account create(AccountDto accountDto) {
        String currencyString = accountDto.getCurrency().toUpperCase();
        boolean isSupported = appCurrencyService.getSupportedCurrencies().stream()
                .anyMatch(c -> c.getCurrencyCode().equalsIgnoreCase(currencyString));

        if (!isSupported) {
            throw new CurrencyIsNotSupportedException(String.format(CURRENCY_IS_NOT_SUPPORTED, currencyString));
        }

        Account account = new Account()
                .setBalance(accountDto.getBalance())
                .setUser(AuthenticationService.getCurrentUser())
                .setCurrency(appCurrencyService.findByCurrencyCode(currencyString));

        return accountRepository.save(account);
    }

    @Transactional
    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format(ACCOUNT_WITH_ID_NOT_FOUND, id));
        }
        accountRepository.deleteById(id);
    }

    @Transactional
    public void updateBalance(Long id, BigDecimal newBalance) {
        Account account = this.findById(id).setBalance(newBalance);
        accountRepository.save(account);
    }
}
