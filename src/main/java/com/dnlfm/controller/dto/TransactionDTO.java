package com.dnlfm.controller.dto;

import com.dnlfm.domain.CurrencyType;
import com.dnlfm.domain.TransactionDirection;

import java.math.BigDecimal;

public class TransactionDTO {

    private Long accountId;
    private Long transactionId;
    private BigDecimal amount;
    private CurrencyType currencyType;
    private TransactionDirection transactionDirection;
    private String description;
    private BigDecimal balanceAfterTransaction;

    public TransactionDTO() {}

    public TransactionDTO(Long transactionId, BigDecimal amount, CurrencyType currencyType, TransactionDirection transactionDirection, String description) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.currencyType = currencyType;
        this.transactionDirection = transactionDirection;
        this.description = description;
    }

    public TransactionDTO(Long transactionId, BigDecimal amount, CurrencyType currencyType, TransactionDirection transactionDirection, String description, BigDecimal balanceAfterTransaction) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.currencyType = currencyType;
        this.transactionDirection = transactionDirection;
        this.description = description;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public TransactionDTO(Long accountId, Long transactionId, BigDecimal amount, CurrencyType currencyType, TransactionDirection transactionDirection, String description) {
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.currencyType = currencyType;
        this.transactionDirection = transactionDirection;
        this.description = description;
    }

    public TransactionDTO(Long accountId, Long transactionId, BigDecimal amount, CurrencyType currencyType, TransactionDirection transactionDirection, String description, BigDecimal balanceAfterTransaction) {
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.currencyType = currencyType;
        this.transactionDirection = transactionDirection;
        this.description = description;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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

    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
}
