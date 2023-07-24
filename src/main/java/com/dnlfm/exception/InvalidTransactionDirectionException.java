package com.dnlfm.exception;

public class InvalidTransactionDirectionException extends RuntimeException {

    public InvalidTransactionDirectionException(String invalidTransactionDirection) {
        super("Invalid transaction direction: " + invalidTransactionDirection);
    }
}
