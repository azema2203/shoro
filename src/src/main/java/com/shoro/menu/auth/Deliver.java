package com.shoro.menu.auth;

import com.shoro.menu.dao.DeliveryDAO;
import com.shoro.menu.dao.OrderDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Deliver extends User {
    private final Connection connection;
    private final DeliveryDAO deliveryDAO;
    private final OrderDAO orderDAO;
    private final Scanner scanner;

    public Deliver(String username, String password, Connection connection) {
        super(username, password, "delivery");
        this.connection = connection;
        this.deliveryDAO = new DeliveryDAO(connection);
        this.orderDAO = new OrderDAO(connection);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showMenu() throws SQLException {
        System.out.println("Приветствую, дорогой доставщик!");

        int choice;
        do {
            printMenu();
            choice = readUserChoice();
            processChoice(choice);
        } while (choice != 7);
    }

    private void printMenu() {
        System.out.println("\n=== Меню доставщика ===");
        System.out.println("1. Показать список товаров для доставки");
        System.out.println("2. Показать доставленные заказы");
        System.out.println("3. Доставить заказ");
        System.out.println("4. Показать количество доставленных товаров");
        System.out.println("5. Показать количество заказанных товаров");
        System.out.println("6. Показать мой заработок");
        System.out.println("7. Выход");
        System.out.print("Выберите опцию: ");
    }

    private int readUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка! Введите число от 1 до 7");
            scanner.next(); // Очистка неверного ввода
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера
        return choice;
    }

    private void processChoice(int choice) throws SQLException {
        switch (choice) {
            case 1 -> showPendingDeliveries();
            case 2 -> showDeliveredOrders();
            case 3 -> deliverOrder();
            case 4 -> showDeliveredCount();
            case 5 -> showOrderedCount();
            case 6 -> showEarnings();
            case 7 -> System.out.println("Возврат в главное меню...");
            default -> System.out.println("Неверная опция!");
        }
    }

    private void showPendingDeliveries() {
        System.out.println("\nСписок товаров для доставки:");
        for (Delivery delivery : deliveryDAO.getPendingDeliveries()) {
            System.out.println(delivery);
        }
    }

    private void showDeliveredOrders() throws SQLException {
        System.out.println("\nДоставленные заказы:");
        for (Delivery delivery : deliveryDAO.getDeliveredOrders()) {
            System.out.println(delivery);
        }
    }

    private void deliverOrder() {
        System.out.print("Введите ID доставки: ");

        // Обработка ввода числа
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка! Введите целое число.");
            scanner.next(); // Очистка неверного ввода
            System.out.print("Введите ID доставки: ");
        }

        int deliveryId = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера после nextInt()

        if (deliveryDAO.markAsDelivered(deliveryId)) {
            System.out.println("Заказ успешно доставлен!");
        } else {
            System.out.println("Заказ с указанным ID не найден");
        }
    }

    private void showDeliveredCount() {
        System.out.println("\nКоличество доставленных товаров: " +
                deliveryDAO.getDeliveredCount());
    }

    private void showOrderedCount() {
        System.out.println("\nКоличество заказанных товаров: " +
                orderDAO.getOrderCount());
    }

    private void showEarnings() {
        System.out.printf("\nВаш заработок: $%.2f\n",
                deliveryDAO.calculateEarnings());
    }

    private void handleDatabaseError(String message, SQLException e) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
    }
}
