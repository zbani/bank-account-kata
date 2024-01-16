package com.kata.bankaccount.services;

import com.kata.bankaccount.models.BankAccount;
import com.kata.bankaccount.models.Statement;
import com.kata.bankaccount.utils.BankAccountUtils;
import com.kata.bankaccount.exceptions.BankAccountException;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BankAccountServiceImpl implements BankAccountService {

    private final StorageService storageService;

    public BankAccountServiceImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public BankAccount deposit(Long id, BigDecimal amount) throws BankAccountException {
        BankAccountUtils.checkAmount(amount);

        BankAccount bankAccount = storageService.getBankAccountById(id);
        BankAccountUtils.checkBankAccount(bankAccount);

        bankAccount.deposit(amount);
        storageService.saveBankAccount(id, bankAccount);

        return bankAccount;
    }

    @Override
    public BankAccount withdraw(Long id, BigDecimal amount) throws BankAccountException {
        BankAccountUtils.checkAmount(amount);

        BankAccount bankAccount = storageService.getBankAccountById(id);
        BankAccountUtils.checkBankAccount(bankAccount);

        bankAccount.withdraw(amount);
        storageService.saveBankAccount(id, bankAccount);

        return bankAccount;
    }

    @Override
    public void seeHistory(Long id) throws BankAccountException {
        BankAccount bankAccount = storageService.getBankAccountById(id);
        BankAccountUtils.checkBankAccount(bankAccount);

        List<Statement> statements = bankAccount.getStatements();

        if (statements.isEmpty()) {
            System.out.print("No operation found.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Operation\t\tDate\t\tAmount\t\tBalance\n");
            sb.append("_______________________________________________\n");
            for (Statement t: statements) {
                sb.append(
                        String.format(
                        "%s\t\t%s\t\t%.2f\t\t%.2f\n",
                        t.getType(),
                        t.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        t.getAmount(),
                        t.getBalance()
                ));
            }
            System.out.print(sb);
        }
    }
}
