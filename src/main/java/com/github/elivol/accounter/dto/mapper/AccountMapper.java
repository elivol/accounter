package com.github.elivol.accounter.dto.mapper;

import com.github.elivol.accounter.dto.model.AccountDto;
import com.github.elivol.accounter.model.Account;

public class AccountMapper {

    public static AccountDto toAccountDto(Account account) {
        return new AccountDto()
                .setId(account.getId())
                .setBalance(account.getBalance())
                .setCurrency(account.getCurrency().getCurrencyCode())
                .setExchangeRate(account.getExchangeRate());
    }
}
