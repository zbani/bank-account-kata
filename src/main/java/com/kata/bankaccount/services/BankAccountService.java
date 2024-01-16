package com.kata.bankaccount.services;

import com.kata.bankaccount.exceptions.BankAccountException;
import com.kata.bankaccount.models.BankAccount;

import java.math.BigDecimal;

public interface BankAccountService {

    BankAccount deposit(Long id, BigDecimal amount) throws BankAccountException;
    BankAccount withdraw(Long id, BigDecimal amount) throws BankAccountException;
    void seeHistory(Long id) throws BankAccountException;

}
