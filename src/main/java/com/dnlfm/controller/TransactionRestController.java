package com.dnlfm.controller;

import com.dnlfm.controller.dto.AccountDTO;
import com.dnlfm.controller.dto.CreateTransactionRequest;
import com.dnlfm.controller.dto.TransactionDTO;
import com.dnlfm.domain.*;
import com.dnlfm.exception.*;
import com.dnlfm.mapper.AccountBalanceMapper;
import com.dnlfm.mapper.AccountMapper;
import com.dnlfm.mapper.TransactionMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/transaction")
@RestController
public class TransactionRestController {

    private final TransactionMapper transactionMapper;
    private final AccountBalanceMapper accountBalanceMapper;
    private final AccountMapper accountMapper;

    public TransactionRestController(TransactionMapper transactionMapper, AccountBalanceMapper accountBalanceMapper, AccountMapper accountMapper) {
        this.transactionMapper = transactionMapper;
        this.accountBalanceMapper = accountBalanceMapper;
        this.accountMapper = accountMapper;
    }

    @PostMapping
    public TransactionDTO createTransaction(@RequestBody CreateTransactionRequest createTransactionRequest)
            throws InvalidCurrencyTypeException, InvalidTransactionDirectionException,
            AccountNotFoundException, AccountBalanceNotFoundException, InvalidAmountException,
            MissingTransactionDescriptionException, InsufficientFundsException {
        Long accountId = createTransactionRequest.getAccountId();
        BigDecimal amount = createTransactionRequest.getAmount();
        CurrencyType currencyType = createTransactionRequest.getCurrencyType();
        TransactionDirection transactionDirection = createTransactionRequest.getTransactionDirection();
        String description = createTransactionRequest.getDescription();

        if (description.trim().length() == 0) {
            throw new MissingTransactionDescriptionException();
        }

        Account loadedAccount = accountMapper.findById(accountId);
        if (loadedAccount == null) {
            throw new AccountNotFoundException(accountId);
        }

        AccountBalance loadedAccountBalance = accountBalanceMapper.findByAccountIdAndCurrencyType(accountId, currencyType);
        if (loadedAccountBalance == null) {
            throw new AccountBalanceNotFoundException(accountId, currencyType);
        }

        if (amount.compareTo(new BigDecimal(0)) <= 0) {
            throw new InvalidAmountException(amount);
        }

        BigDecimal currentFunds = loadedAccountBalance.getAvailableAmount();
        if (transactionDirection.equals(TransactionDirection.OUT) && currentFunds.compareTo(amount) < 0) {
            throw new InsufficientFundsException(amount, currentFunds);
        }

        Transaction transaction = new Transaction(
            new AccountBalance(loadedAccount, currencyType),
            amount,
            transactionDirection,
            description
        );

        boolean isTransactionCreated = false;

        isTransactionCreated = (transactionMapper.insertTransaction(transaction) != 0);

        if (isTransactionCreated) {
            AccountBalance accountBalance = accountBalanceMapper.findByAccountIdAndCurrencyType(accountId, currencyType);
            TransactionDTO transactionDTO = new TransactionDTO(
                    accountId,
                    transaction.getId(),
                    amount,
                    currencyType,
                    transactionDirection,
                    description,
                    accountBalance.getAvailableAmount()
            );
            return transactionDTO;
        } else {
            // TODO: deal with possible issues, e.g. unavailable database
            return null;
        }
    }

    @GetMapping("{accountId}")
    public List<TransactionDTO> getTransaction(@PathVariable("accountId") Long accountId) throws AccountNotFoundException {
        List<Transaction> transactions = transactionMapper.findByAccountId(accountId);
        if (transactions == null) {
            throw new AccountNotFoundException(accountId);
        }
        ArrayList<TransactionDTO> transactionDTOs = new ArrayList<>(transactions.size());
        for (Transaction transaction : transactions) {
            TransactionDTO transactionDTO = new TransactionDTO(
                    transaction.getAccountBalance().getAccount().getId(),
                    transaction.getId(),
                    transaction.getAmount(),
                    transaction.getAccountBalance().getCurrencyType(),
                    transaction.getTransactionDirection(),
                    transaction.getDescription());
            transactionDTOs.add(transactionDTO);
        }
        return transactionDTOs;
    }

}
