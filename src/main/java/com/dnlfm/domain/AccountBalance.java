package com.dnlfm.domain;

import java.math.BigDecimal;

public class AccountBalance {

    private Account account;
    private CurrencyType currencyType;
    private BigDecimal availableAmount;

    public AccountBalance() {}

    public AccountBalance(Account account, CurrencyType currencyType) {
        this.account = account;
        this.currencyType = currencyType;
    }

    public AccountBalance(Account account, CurrencyType currencyType, BigDecimal availableAmount) {
        this.account = account;
        this.currencyType = currencyType;
        this.availableAmount = availableAmount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }
}
