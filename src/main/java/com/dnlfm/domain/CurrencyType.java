package com.dnlfm.domain;

import com.dnlfm.exception.InvalidCurrencyTypeException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CurrencyType {
    EUR ("EUR"),
    SEK ("SEK"),
    GBP ("GBP"),
    USD ("USD");

    private String label;

    CurrencyType(String label) {
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
    public static CurrencyType fromString(String label) throws InvalidCurrencyTypeException {
        try {
            return CurrencyType.valueOf(label.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCurrencyTypeException(label);
        }
    }
}
