package com.example.financeapp.util;

import com.example.financeapp.entity.Category;
import com.example.financeapp.entity.Transaction;
import com.example.financeapp.entity.TransactionType;
import com.example.financeapp.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final TransactionService service;

    public InputHandler(TransactionService service) {
        this.service = service;
    }

    public int showMenu () {
        System.out.println("Меню:");
        System.out.println("1. Добавить транзакцию");
        System.out.println("2. Показать все транзакции");
        System.out.println("3. Фильтр");
        System.out.println("4. Показать баланс");
        System.out.println("0. Выход");

        return Integer.parseInt(scanner.nextLine());
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
    }

    public void handleShowTransactions() {
        if (service.getTransactions().isEmpty()) {
            System.out.println("У вас ещё не было транзакций.");
        } else {
            for (Transaction t: service.getTransactions()) {
                System.out.println(t);
            }
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
                    } catch (Exception e) {
                        System.out.println("Ошибка, попробуйте ещё раз.");
                    }
                }
                break;

            } else if (filterInput.trim().equalsIgnoreCase("сумма")) {
                while (true) {
                    try {
                        System.out.println("Введите нижнюю границу суммы:");
                        BigDecimal amountFrom = new BigDecimal(scanner.nextLine());
                        System.out.println("Введите верхнюю границу суммы:");
                        BigDecimal amountTo = new BigDecimal(scanner.nextLine());
                        service.filterByAmount(amountFrom, amountTo);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка, попробуйте ещё раз.");
                    }
                }
                break;

            } else if (filterInput.trim().equalsIgnoreCase("id")) {
                while (true) {
                    try {
                        System.out.println("Введите id:");
                        String idInput = scanner.nextLine();
                        service.filterById(idInput);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка, попробуйте ещё раз.");
                    }
                }
                break;

            } else if (filterInput.trim().equalsIgnoreCase("категория")) {
                while (true) {
                    try {
                        System.out.println("Введите категорию:");
                        String categoryInput = scanner.nextLine();
                        service.filterByCategory(categoryInput);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка, попробуйте ещё раз.");
                    }
                }
                break;

            } else if (filterInput.trim().equalsIgnoreCase("описание")) {
                while (true) {
                    try {
                        System.out.println("Введите описание:");
                        String descriptionInput = scanner.nextLine();
                        service.filterByDescription(descriptionInput);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка, попробуйте ещё раз.");
                    }
                }
                break;

            } else {
                System.out.println("Ошибка, попробуйте ещё раз.");
            }
        }
    }

    public void handleShowBalance() {
        System.out.println("Ваш баланс: " + service.getBalance());
    }
}
