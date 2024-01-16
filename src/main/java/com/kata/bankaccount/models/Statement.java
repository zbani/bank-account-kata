package com.kata.bankaccount.models;

import com.kata.bankaccount.enums.StatementType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Statement {

    private StatementType type;
    private LocalDateTime date;
    private BigDecimal amount;
    private BigDecimal balance;

    public Statement(StatementType type, LocalDateTime date, BigDecimal amount, BigDecimal balance) {
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }

    public StatementType getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
