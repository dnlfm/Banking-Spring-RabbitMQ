package com.dnlfm.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long invalidAccountId) {
        super("Account not found: " + invalidAccountId);
    }
}
