package com.example.financeapp.controller;
import com.example.financeapp.util.InputHandler;

import java.util.Scanner;

public class AppController {
    private final InputHandler handler;
    private final Scanner scanner = new Scanner(System.in);

    public AppController(InputHandler handler) {
        this.handler = handler;
    }

    public void run() {
        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Добавить транзакцию");
            System.out.println("2. Показать все транзакции");
            System.out.println("3. Фильтр");
            System.out.println("4. Показать баланс");
            System.out.println("5. Изменить баланс");
            System.out.println("0. Выход");

            int input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case 1:
                    handler.handleAddTransaction();
                    break;

                case 2:
                    handler.handleShowTransactions();
                    break;

                case 3:
                    handler.handleFilter();
                    break;

                case 4:
                    handler.handleShowBalance();
                    break;

                case 5:
                    handler.changeBalance();
                    break;

                case 0:
                    return;
            }
        }
    }
}
