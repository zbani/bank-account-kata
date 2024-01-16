package com.kata.bankaccount.models;

import com.kata.bankaccount.enums.StatementType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    BigDecimal balance;
    List<Statement> statements;

    public BankAccount() {
        balance = BigDecimal.ZERO;
        statements = new ArrayList<>();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
        statements.add(new Statement(
                StatementType.DEPOSIT,
                LocalDateTime.now(),
                amount,
                balance
        ));
    }

    public void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
        statements.add(new Statement(
                StatementType.WITHDRAWAL,
                LocalDateTime.now(),
                amount,
                balance
        ));
    }
}
