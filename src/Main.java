import Enums.TransactionType;
import Service.TransactionService;
import Transactions.Transaction;

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
                    int amount = Integer.parseInt(scanner.nextLine());
                    
                    int k = 0;
                    TransactionType transactionType = null;
                    while (k == 0) {
                        System.out.println("Введите тип транзакции(доход, расход)");
                        String transactionTypeInput = scanner.nextLine();
                        if (transactionTypeInput.equalsIgnoreCase("доход")) {
                            transactionType = TransactionType.INCOME;
                            k = 1;
                        } else if (transactionTypeInput.equalsIgnoreCase("расход")) {
                            transactionType = TransactionType.EXPENSE;
                            k = 1;
                        } else {
                            System.out.println("Ошибка, попробуйте ещё раз.");
                        }
                    }
                    
                    System.out.println("Введите категорию транзакции:");
                    String category = scanner.nextLine();
                    
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
                    int k1 = 0;
                    while (k1 == 0) {
                        System.out.println("Введите фильтр (тип, категория)");
                        String input1 = scanner.nextLine();
                        if (input1.equalsIgnoreCase("тип")) {
                            int k2 = 0;
                            while (k2 == 0) {
                                System.out.println("Введите тип (доход, расход):");
                                String typeInput = scanner.nextLine();
                                if (typeInput.equalsIgnoreCase("доход")) {
                                    transactionService.filterByTypeIncome();
                                    k2 = 1;
                                } else if (typeInput.equalsIgnoreCase("расход")) {
                                    transactionService.filterByTypeExpense();
                                    k2 = 1;
                                } else {
                                    System.out.println("Ошибка, попробуйте ещё раз.");
                                }
                            }
                            k1 = 1;
                        } else if (input1.equalsIgnoreCase("категория")) {
                            System.out.println("Введите категорию:");
                            String categoryInput = scanner.nextLine();
                            transactionService.filterByCategory(categoryInput);
                            k1 = 1;
                        } else {
                            System.out.println("Ошибка, попробуйте ещё раз.");
                        }
                    }
                    break;

                case 4:
                    System.out.println("Ваш баланс: " + transactionService.getBalance());
                    int k3 = 0;
                    while (k3 == 0) {
                        System.out.println("Вы хотите изменить баланс?");
                        String answer = scanner.nextLine();
                        if (answer.equalsIgnoreCase("да")) {
                            System.out.println("Введите сумму баланса:");
                            int balance = Integer.parseInt(scanner.nextLine());
                            transactionService.setBalance(balance);
                            System.out.println("Ваш баланс: " + transactionService.getBalance());
                            k3 = 1;
                        } else if (answer.equalsIgnoreCase("нет")) {
                            k3 = 1;
                        } else {
                            System.out.println("Пожалуйста, ответьте да/нет");
                        }
                    }
                    break;

                case 0:
                    return;
            }
        }
    }
}
