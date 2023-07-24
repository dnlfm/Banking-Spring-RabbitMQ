package com.dnlfm.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

    private Long id;
    private AccountBalance accountBalance;
    private BigDecimal amount;
    private TransactionDirection transactionDirection;
    private String description;
    private Date createdAt;

    public Transaction() {}

    public Transaction(Long id) {
        this.id = id;
    }

    public Transaction(AccountBalance accountBalance, BigDecimal amount, TransactionDirection transactionDirection, String description) {
        this.accountBalance = accountBalance;
        this.amount = amount;
        this.transactionDirection = transactionDirection;
        this.description = description;
    }

    public Transaction(AccountBalance accountBalance, BigDecimal amount, TransactionDirection transactionDirection, String description, Date createdAt) {
        this.accountBalance = accountBalance;
        this.amount = amount;
        this.transactionDirection = transactionDirection;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Transaction(Long id, AccountBalance accountBalance, BigDecimal amount, TransactionDirection transactionDirection, String description, Date createdAt) {
        this.id = id;
        this.accountBalance = accountBalance;
        this.amount = amount;
        this.transactionDirection = transactionDirection;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountBalance getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(AccountBalance accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
