package Service;

import Enums.TransactionType;
import Transactions.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    public List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction, int amount, int id, TransactionType transactionType, String category, String description, LocalDateTime dateTime) {
        if (transactions.isEmpty()) {
            id = 0;
        } else {
            Transaction lastTransaction = transactions.getLast();
            id = lastTransaction.getId() + 1;
        }

        dateTime = LocalDateTime.now();

        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setCategory(category);
        transaction.setDescription(description);
        transaction.setId(id);
        transaction.setDateTime(dateTime);
        transactions.add(transaction);
    }


}
