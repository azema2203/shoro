package com.shoro.menu.auth;

import com.shoro.menu.dao.ProductDAO;
import java.sql.Connection;
import java.util.Scanner;

public class Provider extends User {
    private final Scanner scanner;
    private final ProductDAO productDAO;

    public Provider(String username, String password, Connection connection) {
        super(username, password, "supplier");
        this.scanner = new Scanner(System.in);
        this.productDAO = new ProductDAO(connection); // Передаем соединение в DAO
    }

    @Override
    public void showMenu() {
        System.out.println("Приветствую, дорогой поставщик!");

        while (true) {
            printMenu();
            int choice = readUserChoice();
            if (choice == 5) break;
            processChoice(choice);
        }
    }

    private void printMenu() {
        System.out.println("\n=== Меню поставщика ===");
        System.out.println("1. Показать список товаров для поставки");
        System.out.println("2. Показать количество поставляемого товара");
        System.out.println("3. Показать самый популярный товар");
        System.out.println("4. Показать наименее популярный товар");
        System.out.println("5. Выход");
        System.out.print("Выберите опцию: ");
    }
    private int readUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка! Введите число от 1 до 6");
            scanner.next(); // Очистка неверного ввода
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера
        return choice;
    }
    private void processChoice(int choice) {
        switch (choice) {
            case 1 -> showProductsForSupply();
            case 2 -> showTotalProductsForSupply();
            case 3 -> showMostOrderedProduct();
            case 4 -> showLeastOrderedProduct();
            case 5 -> System.out.println("Возврат в главное меню...");
            default -> System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
        }
    }

    private void showProductsForSupply() {
        System.out.println("\nСписок товаров для поставки:");
        productDAO.getNeededProducts().forEach(System.out::println);
    }

    private void showTotalProductsForSupply() {
        System.out.printf("\nОбщее количество товаров для поставки: %d\n",
                productDAO.getNeededProductsCount());
    }

    private void showMostOrderedProduct() {
        System.out.println("\nСамый популярный товар:");
        Product product = productDAO.getMostOrderedProduct();
        if (product != null) {
            System.out.println(product);
        } else {
            System.out.println("Данные отсутствуют");
        }
    }


    private void showLeastOrderedProduct() {
        System.out.println("\nНаименее популярный товар:");
        Product product = productDAO.getLeastOrderedProduct();
        if (product != null) {
            System.out.println(product);
        } else {
            System.out.println("Данные отсутствуют");
        }
    }

}
