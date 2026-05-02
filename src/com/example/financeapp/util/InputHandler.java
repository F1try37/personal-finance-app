package com.example.financeapp.util;

import com.example.financeapp.entity.Category;
import com.example.financeapp.entity.TransactionType;
import com.example.financeapp.service.TransactionService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Scanner;

public class InputHandler {
    Scanner scanner = new Scanner(System.in);
    private final TransactionService service;

    public InputHandler(TransactionService service) {
        this.service = service;
    }

    public void handleAddTransaction() {
        BigDecimal amount = null;
        while (amount == null) {
            try {
                System.out.println("Введите сумму:");
                String amountInput = scanner.nextLine();
                amount = new BigDecimal(amountInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка ввода, попробуйте ещё раз.");
            }
        }

        TransactionType transactionType = null;

        while (transactionType == null) {
            try {
                System.out.println("Введите тип транзакции(доход, расход)");
                String transactionTypeInput = scanner.nextLine();
                if (transactionTypeInput.trim().equalsIgnoreCase("доход")) {
                    transactionType = TransactionType.INCOME;
                } else if (transactionTypeInput.trim().equalsIgnoreCase("расход")) {
                    transactionType = TransactionType.EXPENSE;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Неверная категория, попробуйте ещё раз.");
            }
        }

        System.out.println("Введите категорию транзакции:");
        System.out.println("Список доступных категорий:");
        for (Category c: Category.values()) {
            System.out.println("- " + c.name());
        }
        Category category = null;
        while (category == null) {
            String categoryInput = scanner.nextLine();
            try {
                category = Category.valueOf(categoryInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Неверная категория, попробуйте ещё раз.");
            }
        }

        System.out.println("Введите описание транзакции:");
        String description = scanner.nextLine();

        service.addTransaction(amount, transactionType, category, description);
        service.balanceCount();
    }

    public void handleShowTransactions() {
        if (service.getTransactions().isEmpty()) {
            System.out.println("У вас ещё не было транзакций.");
        } else {
            System.out.println(service.getTransactions());
        }
    }

    public void handleFilter() {
        while (true) {
            System.out.println("Введите фильтр:");
            String filterInput = scanner.nextLine().trim();

            if (filterInput.equalsIgnoreCase("тип")) {

                while (true) {
                    System.out.println("Введите тип (доход, расход):");
                    String typeInput = scanner.nextLine().trim();

                    if (typeInput.equalsIgnoreCase("доход")) {
                        service.filterByTypeIncome();
                        break;
                    } else if (typeInput.equalsIgnoreCase("расход")) {
                        service.filterByTypeExpense();
                        break;
                    } else {
                        System.out.println("Ошибка, попробуйте ещё раз.");
                    }
                }

                break;

            } else if (filterInput.trim().equalsIgnoreCase("дата") || filterInput.trim().equalsIgnoreCase("время")) {
                while (true) {
                    try {
                        System.out.println("Введите начало периода ГГГГ-ММ-ДДTЧЧ:мм:сс (где T — введите английскую T):");
                        LocalDateTime dateFrom = LocalDateTime.parse(scanner.nextLine());
                        System.out.println("Введите конец периода ГГГГ-ММ-ДДTЧЧ:мм:сс (где T — введите английскую T):");
                        LocalDateTime dateTo = LocalDateTime.parse(scanner.nextLine());
                        service.filterByDateTime(dateFrom, dateTo);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка, попробуйте ещё раз.");
                    }
                }
                break;

            } else if (filterInput.trim().equalsIgnoreCase("сумма")) {
                while (true) {
                    try {
                        System.out.println("Введите нижнюю границу суммы:");
                        BigDecimal amountFrom = new BigDecimal(BigInteger.valueOf(Long.parseLong(scanner.nextLine())));
                        System.out.println("Введите верхнюю границу суммы:");
                        BigDecimal amountTo = new BigDecimal(BigInteger.valueOf(Long.parseLong(scanner.nextLine())));
                        service.filterByAmount(amountFrom, amountTo);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка, попробуйте ещё раз.");
                    }
                }
                break;

            } else if (filterInput.trim().equalsIgnoreCase("категория") || filterInput.trim().equalsIgnoreCase("id") || filterInput.trim().equalsIgnoreCase("описание")) {
                service.filterByType(filterInput);
                break;

            } else {
                System.out.println("Ошибка, попробуйте ещё раз.");
            }
        }
    }

    public void handleShowBalance() {
        System.out.println("Ваш баланс: " + service.getBalance());
    }

    public void changeBalance() {
        BigDecimal balance = null;

        while (balance == null) {
            System.out.println("Введите сумму баланса:");
            String balanceInput = scanner.nextLine().trim();

            try {
                balance = new BigDecimal(balanceInput);
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат числа, попробуйте ещё раз.");
            }
        }

        service.setBalance(balance);
        System.out.println("Ваш баланс: " + service.getBalance());
    }
}
