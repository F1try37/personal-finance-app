package com.example.financeapp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private BigDecimal amount;
    private TransactionType transactionType;
    private Category category;
    private String description;
    private LocalDateTime dateTime;

    public Transaction(BigDecimal amount, int id, TransactionType transactionType, Category category, String description, LocalDateTime dateTime) {
        this.amount = amount;
        this.id = id;
        this.transactionType = transactionType;
        this.category = category;
        this.description = description;
        this.dateTime = dateTime;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getString() {
        return "id: " + id + ", сумма: " + amount + ", тип: " + transactionType + ", категория: " + category + ", описание: " + description + ", дата: " + dateTime;
    }
}
