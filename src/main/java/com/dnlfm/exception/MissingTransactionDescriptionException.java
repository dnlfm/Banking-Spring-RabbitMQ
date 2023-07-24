package com.dnlfm.exception;

public class MissingTransactionDescriptionException extends RuntimeException {

    public MissingTransactionDescriptionException() {
        super("Transaction description is missing.");
    }
}