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

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public BigDecimal getBalance() {
        return repository.getTransactions().stream().map(t -> t.getTransactionType().equals(TransactionType.INCOME) ? t.getAmount() : t.getAmount().negate()).reduce(BigDecimal.ZERO, BigDecimal::add);
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
                System.out.println(transaction);
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }

    public void filterByTypeExpense () {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getTransactionType().equals(TransactionType.EXPENSE)) {
                System.out.println(transaction);
                k++;
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }

    public void filterById (String input) {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getId() == Integer.parseInt(input)) {
                System.out.println(transaction);
                k++;
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }

    public void filterByCategoryName(Category category) {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getCategory().equals(category)) {
                System.out.println(transaction);
                k++;
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }

    public void filterByCategoryId(Category category) {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getCategory().equals(category)) {
                System.out.println(transaction);
                k++;
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }

    public void filterByDescription(String input) {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getDescription().contains(input)) {
                System.out.println(transaction);
                k++;
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }

    public void filterByDateTime (LocalDateTime from, LocalDateTime to) {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getDateTime().isAfter(from) && transaction.getDateTime().isBefore(to)) {
                System.out.println(transaction);
                k++;
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }

    public void filterByAmount(BigDecimal fromSum, BigDecimal toSum) {
        int k = 0;
        for (Transaction transaction: repository.getTransactions()) {
            if (transaction.getAmount().compareTo(fromSum) >= 0 && transaction.getAmount().compareTo(toSum) <= 0) {
                System.out.println(transaction);
                k++;
            }
        }
        if (k == 0) System.out.println("Ничего не найдено.");
    }
}
