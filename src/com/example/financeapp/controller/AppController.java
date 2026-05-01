package com.example.financeapp.controller;

import com.example.financeapp.entity.Category;
import com.example.financeapp.entity.TransactionType;
import com.example.financeapp.service.TransactionService;

import java.math.BigDecimal;
import java.util.Scanner;

public class AppController {

    private final TransactionService service;
    private final Scanner scanner = new Scanner(System.in);

    public AppController(TransactionService service) {
        this.service = service;
    }

    public void run() {
        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Добавить транзакцию");
            System.out.println("2. Показать все транзакции");
            System.out.println("3. Фильтр");
            System.out.println("4. Баланс");
            System.out.println("0. Выход");

            int input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case 1:
                    handleAddTransaction();
                    break;

                case 2:
                    handleShowTransactions();
                    break;

                case 3:
                    handleFilter();
                    break;

                case 4:
                    handleShowBalance();
                    break;

                case 0:
                    return;
            }
        }
    }

    private void handleAddTransaction() {
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
    }

    private void handleShowTransactions() {
        if (service.getTransactions().isEmpty()) {
            System.out.println("У вас ещё не было транзакций.");
        } else {
            System.out.println(service.getTransactions());
        }
    }

    private void handleFilter() {
        while (true) {
            System.out.println("Введите фильтр (тип, категория):");
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

            } else if (filterInput.equalsIgnoreCase("категория")) {

                Category category1 = null;

                while (category1 == null) {
                    System.out.println("Доступные категории:");
                    for (Category c : Category.values()) {
                        System.out.println("- " + c.name().toLowerCase());
                    }

                    System.out.println("Введите категорию:");
                    String categoryInput1 = scanner.nextLine();

                    try {
                        category1 = Category.valueOf(categoryInput1.trim().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Неверная категория, попробуйте ещё раз.");
                    }
                }

                service.filterByCategory(category1);
                break;

            } else {
                System.out.println("Ошибка, попробуйте ещё раз.");
            }
        }
    }

    private void handleShowBalance() {
        System.out.println("Ваш баланс: " + service.getBalance());

        while (true) {
            System.out.println("Вы хотите изменить баланс? (да/нет)");
            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase("да")) {

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
                break;

            } else if (answer.equalsIgnoreCase("нет")) {
                break;

            } else {
                System.out.println("Пожалуйста, ответьте да или нет.");
            }
        }
    }
}
