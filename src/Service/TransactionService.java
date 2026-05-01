package Service;

import Enums.Category;
import Enums.TransactionType;
import Transactions.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    public List<Transaction> transactions = new ArrayList<>();
    BigDecimal balance = BigDecimal.valueOf(0);

    public TransactionService() {
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void addTransaction(BigDecimal amount, int id, TransactionType transactionType, Category category, String description, LocalDateTime dateTime) {
        transactions.add(new Transaction(amount,id,transactionType,category,description,dateTime));
    }

    public void getTransactions() {
        for (Transaction transaction: transactions) {
            System.out.println(transaction.toString());
        }
    }

    public void filterByTypeIncome () {
        int k = 0;
        for (Transaction transaction: transactions) {
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
        for (Transaction transaction: transactions) {
            if (transaction.getTransactionType().equals(TransactionType.EXPENSE)) {
                System.out.println(transaction.toString());
                k++;
            }
        }
        if (k == 0) {
            System.out.println("Ничего не найдено.");
        }
    }

    public void filterByCategory(String input) {
        int k = 0;
        for (Transaction transaction: transactions) {
            if (transaction.getCategory().equals(Category.valueOf(input.toUpperCase()))) {
                System.out.println(transaction.toString());
                k++;
            }
        }
        if (k == 0) {
            System.out.println("Ничего не найдено.");
        }
    }
}
