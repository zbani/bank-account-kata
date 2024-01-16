package com.kata.bankaccount.utils;

import com.kata.bankaccount.exceptions.BankAccountException;
import com.kata.bankaccount.models.BankAccount;

import java.math.BigDecimal;

public class BankAccountUtils {

    public static void checkAmount(BigDecimal amount) throws BankAccountException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BankAccountException("Amount must be greater than zero.");
        }
    }

    public static void checkBankAccount(BankAccount bankAccount) throws BankAccountException {
        if (bankAccount == null) {
            throw new BankAccountException("Forbidden access.");
        }
    }

}
