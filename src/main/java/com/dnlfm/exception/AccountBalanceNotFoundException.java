package com.dnlfm.exception;

import com.dnlfm.domain.CurrencyType;

public class AccountBalanceNotFoundException extends RuntimeException {

    public AccountBalanceNotFoundException(Long accountId, CurrencyType currencyType) {
        super("Account balance does not exist for account " + accountId + " and currency " + currencyType);
    }
}
