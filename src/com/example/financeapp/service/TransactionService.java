package com.example.financeapp.service;

import com.example.financeapp.entity.Category;
import com.example.financeapp.entity.TransactionType;
import com.example.financeapp.entity.Transaction;
import com.example.financeapp.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private final TransactionRepository repository;
    private BigDecimal balance = BigDecimal.valueOf(0);

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void addTransaction(BigDecimal amount, TransactionType transactionType, Category category, String description) {
        int id;
        if (repository.getTransactions().isEmpty()) {
            id = 0;
        } else {
            Transaction lastTransaction = repository.getTransactions().getLast();
            id = lastTransaction.getId() + 1;
        }

        LocalDateTime dateTime = LocalDateTime.now();

        repository.getTransactions().add(new Transaction(amount,id,transactionType,category,description,dateTime));
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(repository.getTransactions());
    }

    public void filterByTypeIncome () {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getTransactionType().equals(TransactionType.INCOME)) {
                k++;
                System.out.println(transaction.toString());
            }
        }
        if (k == 0) {
            System.out.println("Ничего не найдено.");
        }
    }

    public void filterByTypeExpense () {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getTransactionType().equals(TransactionType.EXPENSE)) {
                System.out.println(transaction.toString());
                k++;
            }
        }
        if (k == 0) {
            System.out.println("Ничего не найдено.");
        }
    }

    public void filterByCategory(Category category) {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getCategory().equals(category)) {
                System.out.println(transaction.toString());
                k++;
            }
        }
        if (k == 0) {
            System.out.println("Ничего не найдено.");
        }
    }
}
