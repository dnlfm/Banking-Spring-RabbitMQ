package com.dnlfm.controller.dto;

import com.dnlfm.domain.CurrencyType;

import java.math.BigDecimal;

public class AccountBalanceDTO {

    private CurrencyType currencyType;
    private BigDecimal availableAmount;

    public AccountBalanceDTO() {}

    public AccountBalanceDTO(CurrencyType currencyType, BigDecimal availableAmount) {
        this.currencyType = currencyType;
        this.availableAmount = availableAmount;
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
