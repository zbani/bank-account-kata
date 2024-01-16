package com.kata.bankaccount.services;

import com.kata.bankaccount.models.BankAccount;

public interface StorageService {

    BankAccount getBankAccountById(Long id);
    void saveBankAccount(Long id, BankAccount bankAccount);

}
