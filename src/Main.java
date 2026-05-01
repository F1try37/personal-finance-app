import Enums.Category;
import Enums.TransactionType;
import Service.TransactionService;
import Transactions.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        TransactionService transactionService = new TransactionService();

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
                    System.out.println("Введите сумму:");
                    BigDecimal amount = null;
                    while (amount == null) {
                        try {
                            amount = scanner.nextBigDecimal();
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
                    String categoryInput = scanner.nextLine();
                    Category category = null;
                    while (category == null) {
                        try {
                            category = Category.valueOf(categoryInput.toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Неверная категория, попробуйте ещё раз.");
                        }
                    }
                    
                    System.out.println("Введите описание транзакции:");
                    String description = scanner.nextLine();
                    
                    int id;
                    LocalDateTime dateTime = LocalDateTime.now();
                    if (transactionService.transactions.isEmpty()) {
                        id = 0;
                    } else {
                        Transaction lastTransaction = transactionService.transactions.getLast();
                        id = lastTransaction.getId() + 1;
                    }
                    
                    transactionService.addTransaction(amount, id, transactionType, category, description, dateTime);
                    break;

                case 2:
                    if (transactionService.transactions.isEmpty()) {
                        System.out.println("У вас ещё не было транзакций.");
                    } else {
                        transactionService.getTransactions();
                    }
                    break;

                case 3:
                    while (true) {
                        System.out.println("Введите фильтр (тип, категория):");
                        String TypeInput = scanner.nextLine().trim();

                        if (TypeInput.equalsIgnoreCase("тип")) {

                            while (true) {
                                System.out.println("Введите тип (доход, расход):");
                                String typeInput = scanner.nextLine().trim();

                                if (typeInput.equalsIgnoreCase("доход")) {
                                    transactionService.filterByTypeIncome();
                                    break;
                                } else if (typeInput.equalsIgnoreCase("расход")) {
                                    transactionService.filterByTypeExpense();
                                    break;
                                } else {
                                    System.out.println("Ошибка, попробуйте ещё раз.");
                                }
                            }

                            break;

                        } else if (TypeInput.equalsIgnoreCase("категория")) {

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

                            transactionService.filterByCategory(category1.name());
                            break;

                        } else {
                            System.out.println("Ошибка, попробуйте ещё раз.");
                        }
                    }

                case 4:
                    System.out.println("Ваш баланс: " + transactionService.getBalance());

                    while (true) {
                        System.out.println("Вы хотите изменить баланс? (да/нет)");
                        String answer = scanner.nextLine().trim();

                        if (answer.equalsIgnoreCase("да")) {

                            BigDecimal balance = null;

                            while (balance == null) {
                                System.out.println("Введите сумму баланса:");
                                String BalanceInput = scanner.nextLine().trim();

                                try {
                                    balance = new BigDecimal(BalanceInput);
                                } catch (NumberFormatException e) {
                                    System.out.println("Неверный формат числа, попробуйте ещё раз.");
                                }
                            }

                            transactionService.setBalance(balance);
                            System.out.println("Ваш баланс: " + transactionService.getBalance());
                            break;

                        } else if (answer.equalsIgnoreCase("нет")) {
                            break;

                        } else {
                            System.out.println("Пожалуйста, ответьте да или нет.");
                        }
                    }
                    break;

                case 0:
                    return;
            }
        }
    }
}
