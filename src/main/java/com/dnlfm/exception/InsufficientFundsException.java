package com.dnlfm.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(BigDecimal amount, BigDecimal currentFunds) {
        super("Insufficient funds: can't do operation to withdraw " + amount + " having " + currentFunds + ".");
    }
}