package com.dnlfm.controller.dto;

import com.dnlfm.domain.CurrencyType;
import com.dnlfm.domain.TransactionDirection;

import java.math.BigDecimal;

public class CreateTransactionRequest {

    private Long accountId;
    private BigDecimal amount;
    private CurrencyType currencyType;
    private TransactionDirection transactionDirection;
    private String description;

    public CreateTransactionRequest() {}

    public CreateTransactionRequest(Long accountId, BigDecimal amount, CurrencyType currencyType, TransactionDirection transactionDirection, String description) {
        this.accountId = accountId;
        this.amount = amount;
        this.currencyType = currencyType;
        this.transactionDirection = transactionDirection;
        this.description = description;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public TransactionDirection getTransactionDirection() {
        return transactionDirection;
    }

    public void setTransactionDirection(TransactionDirection transactionDirection) {
        this.transactionDirection = transactionDirection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
