package com.shoro.menu;

import com.shoro.auth.User;
import com.shoro.database.DBConnection;
import com.shoro.database.ProductDAO;
import com.shoro.utils.ConsoleUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ProviderMenu {
    private final User user;
    private final Scanner scanner;
    private final ProductDAO productDAO;

    public ProviderMenu(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
        this.productDAO = new ProductDAO();
    }

    public void showMenu() {
        System.out.println("Приветствую дорогой, Поставщик!");

        int choice;
        do {
            System.out.println("\nПожалуйста наберите номер меню для работы с программой, если закончили, то наберите 5:");
            System.out.println("1. Показать список товаров для поставки");
            System.out.println("2. Показать количество поставляемого товара");
            System.out.println("3. Показать товар с самым большим количеством заказов");
            System.out.println("4. Показать товар с самым меньшим количеством заказов");
            System.out.println("5. Выход");

            choice = ConsoleUtils.readInt(scanner, "Выбор меню: ");

            switch (choice) {
                case 1:
                    showProductsForSupply();
                    break;
                case 2:
                    showTotalProductsForSupply();
                    break;
                case 3:
                    showMostOrderedProduct();
                    break;
                case 4:
                    showLeastOrderedProduct();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        } while (choice != 5);
    }

    private void showProductsForSupply() {
        System.out.println("\nСписок товаров для поставки:");
        productDAO.getNeededProducts().forEach(System.out::println);
    }

    private void showTotalProductsForSupply() {
        System.out.println("\nОбщее количество товаров для поставки: " +
                productDAO.getNeededProductsCount());
    }

    private void showMostOrderedProduct() {
        System.out.println("\nТовар с самым большим количеством заказов:");
        System.out.println(productDAO.getMostOrderedProduct());
    }

    private void showLeastOrderedProduct() {
        System.out.println("\nТовар с самым меньшим количеством заказов:");
        System.out.println(productDAO.getLeastOrderedProduct());
    }
}
