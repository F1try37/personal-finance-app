package Transactions;

import Enums.TransactionType;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private int amount;
    private TransactionType transactionType;
    private String category;
    private String description;
    private LocalDateTime dateTime;

    public Transaction(int amount, int id, TransactionType transactionType, String category, String description, LocalDateTime dateTime) {
        this.amount = amount;
        this.id = id;
        this.transactionType = transactionType;
        this.category = category;
        this.description = description;
        this.dateTime = dateTime;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setCategory(String category) {
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
}
