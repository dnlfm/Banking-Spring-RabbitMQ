package com.dnlfm.controller.dto;

import com.dnlfm.domain.Account;
import com.dnlfm.domain.AccountBalance;
import com.dnlfm.domain.Customer;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDTO {

    private Long id; // to simplify, account number and id are the same
    private Long customer_id;
    private List<AccountBalanceDTO> accountBalanceDTOs;

    public AccountDTO() {}

    public AccountDTO(Account account, List<AccountBalance> accountBalances) {
        this.id = account.getId();
        this.customer_id = account.getCustomer().getId();
        setAccountBalanceDTOs(accountBalances);
    }

    public AccountDTO(Long id, Long customer_id, List<AccountBalanceDTO> accountBalanceDTOs) {
        this.id = id;
        this.customer_id = customer_id;
        this.accountBalanceDTOs = accountBalanceDTOs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public List<AccountBalanceDTO> getAccountBalanceDTOs() {
        return accountBalanceDTOs;
    }

    public void setAccountBalanceDTOs(List<AccountBalance> accountBalances) {
        this.accountBalanceDTOs = accountBalances.stream().map(accountBalance -> new AccountBalanceDTO(accountBalance.getCurrencyType(), accountBalance.getAvailableAmount())).collect(Collectors.toList());
    }
}
