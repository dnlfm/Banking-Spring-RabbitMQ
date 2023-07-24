package com.dnlfm.controller;

import com.dnlfm.controller.dto.AccountDTO;
import com.dnlfm.controller.dto.CreateAccountRequest;
import com.dnlfm.domain.Account;
import com.dnlfm.domain.AccountBalance;
import com.dnlfm.domain.CurrencyType;
import com.dnlfm.domain.Customer;
import com.dnlfm.exception.AccountNotFoundException;
import com.dnlfm.mapper.AccountBalanceMapper;
import com.dnlfm.mapper.AccountMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RequestMapping("/account")
@RestController
public class AccountRestController {

    private final AccountMapper accountMapper;
    private final AccountBalanceMapper accountBalanceMapper;

    public AccountRestController(AccountMapper accountMapper, AccountBalanceMapper accountBalanceMapper) {
        this.accountMapper = accountMapper;
        this.accountBalanceMapper = accountBalanceMapper;
    }

    @PostMapping
    public AccountDTO createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        Set<CurrencyType> currencyTypes = new HashSet<>(createAccountRequest.getCurrencyTypes());
        // Create and save the account
        Account account = new Account(new Customer(createAccountRequest.getCustomerId()), createAccountRequest.getCountry());
        boolean isAccountCreated = (accountMapper.insertAccount(account) != 0);

        ArrayList<AccountBalance> addedAccountBalances = new ArrayList<>();

        if (isAccountCreated) {
            // Create and save the account balances
            for (CurrencyType currencyType : currencyTypes) {
                AccountBalance accountBalance = new AccountBalance(account, currencyType, new BigDecimal(0));
                boolean isAccountBalanceCreated = (accountBalanceMapper.insertAccountBalance(accountBalance) != 0);
                if (isAccountBalanceCreated) {
                    addedAccountBalances.add(accountBalance);
                } else {
                    // TODO: deal with possible issues, e.g. unavailable database
                }
            }
        }

        AccountDTO accountDTO = new AccountDTO(account, addedAccountBalances);
        return accountDTO;
    }

    @GetMapping("{accountId}")
    public AccountDTO getAccount(@PathVariable("accountId") Long accountId) throws AccountNotFoundException {
        Account account = accountMapper.findById(accountId);
        if (account == null) {
            throw new AccountNotFoundException(accountId);
        }
        List<AccountBalance> accountBalances = accountBalanceMapper.findByAccountId(accountId);
        AccountDTO accountDTO = new AccountDTO(account, accountBalances);
        return accountDTO;
    }
}
