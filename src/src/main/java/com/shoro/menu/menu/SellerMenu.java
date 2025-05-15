package com.shoro.menu.menu;

import com.shoro.menu.auth.User;
import com.shoro.menu.db.DBConnection;
import com.shoro.menu.db.ProductDAO;
import com.shoro.menu.db.OrderDAO;
import com.shoro.menu.utils.ConsoleUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class SellerMenu {
    private final User user;
    private final Scanner scanner;
    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;

    public SellerMenu(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
        this.productDAO = new ProductDAO();
        this.orderDAO = new OrderDAO();
    }

    public void showMenu() {
        System.out.println("Приветствую дорогой, Продавец!");

        int choice;
        do {
            System.out.println("\nПожалуйста наберите номер меню для работы с программой, если закончили, то наберите 6:");
            System.out.println("1. Показать весь список товаров для продажи");
            System.out.println("2. Искать товар");
            System.out.println("3. Показать отчет по продаже");
            System.out.println("4. Сделать заказ отсутствующего товара");
            System.out.println("5. Удалить заказ");
            System.out.println("6. Выход");

            choice = ConsoleUtils.readInt(scanner, "Выбор меню: ");

            switch (choice) {
                case 1:
                    showAllProducts();
                    break;
                case 2:
                    searchProduct();
                    break;
                case 3:
                    showSoldProducts();
                    break;
                case 4:
                    orderMissingProduct();
                    break;
                case 5:
                    deleteOrder();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        } while (choice != 6);
    }

    private void showAllProducts() {
        System.out.println("\nВесь список товаров для продажи:");
        productDAO.getAllAvailableProducts().forEach(System.out::println);
    }

    private void searchProduct() {
        System.out.println("\nИскать товар:");
        System.out.println("1. По названию");
        System.out.println("2. По дате");
        int searchChoice = ConsoleUtils.readInt(scanner, "Выберите вариант поиска: ");

        switch (searchChoice) {
            case 1:
                String name = ConsoleUtils.readString(scanner, "Напишите название товара для поиска: ");
                productDAO.getProductsByName(name).forEach(System.out::println);
                break;
            case 2:
                String date = ConsoleUtils.readString(scanner, "Напишите дату для поиска (гггг-мм-дд): ");
                productDAO.getProductsByDate(date).forEach(System.out::println);
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private void showSoldProducts() {
        System.out.println("\nОтчет по продажам:");
        orderDAO.getAllOrders().forEach(System.out::println);
    }

    private void orderMissingProduct() {
        System.out.println("\nСделать заказ отсутствующего товара:");
        String productName = ConsoleUtils.readString(scanner, "Введите название товара: ");
        int quantity = ConsoleUtils.readInt(scanner, "Введите количество: ");

        try (Connection conn = DBConnection.getConnection()) {
            productDAO.addProduct(conn, productName, 0.0, "NEEDED", quantity);
            System.out.println("Заказ успешно создан.");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании заказа: " + e.getMessage());
        }
    }

    private void deleteOrder() {
        System.out.println("\nУдалить заказ:");
        int orderId = ConsoleUtils.readInt(scanner, "Введите ID заказа для удаления: ");

        if (orderDAO.deleteOrder(orderId)) {
            System.out.println("Заказ успешно удален.");
        } else {
            System.out.println("Не удалось удалить заказ.");
        }
    }
}
