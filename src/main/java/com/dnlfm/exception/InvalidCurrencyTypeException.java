package com.dnlfm.exception;

public class InvalidCurrencyTypeException extends RuntimeException {

    public InvalidCurrencyTypeException(String invalidCurrencyType) {
        super("Invalid currency type: " + invalidCurrencyType);
    }
}