package com.dnlfm.domain;

import com.dnlfm.exception.InvalidTransactionDirectionException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionDirection {
    IN ("IN"),
    OUT ("OUT");

    private String label;

    TransactionDirection(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }

    @JsonCreator
    public static TransactionDirection fromString(String label) throws InvalidTransactionDirectionException {
        try {
            return TransactionDirection.valueOf(label.toUpperCase());
        } catch (Exception e) {
            throw new InvalidTransactionDirectionException(label);
        }
    }
}
