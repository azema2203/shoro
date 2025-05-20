package com.shoro.menu.auth;

import com.shoro.menu.dao.OrderDAO;
import com.shoro.menu.dao.ProductDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Seller extends User {
    private final Connection connection;
    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;
    private final Scanner scanner;

    public Seller(String username, String password, Connection connection) {
        super(username, password, "seller");
        this.connection = connection;
        this.orderDAO = new OrderDAO(connection);
        this.productDAO = new ProductDAO(connection);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showMenu() throws SQLException {
        System.out.println("Приветствую, дорогой продавец!");
        while (true) {
            printMenu();
            int choice = readUserChoice();
            if (choice == 6) break;
            processChoice(choice);
        }
    }

    private void printMenu() {
        System.out.println("\n=== Меню продавца ===");
        System.out.println("1. Показать весь список товаров для продажи");
        System.out.println("2. Искать товар");
        System.out.println("3. Показать отчет по продаже");
        System.out.println("4. Сделать заказ отсутствующего товара");
        System.out.println("5. Удалить заказ");
        System.out.println("6. Выход");
        System.out.print("Выберите опцию: ");
    }

    private int readUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка! Введите число от 1 до 6");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private void processChoice(int choice) throws SQLException {
        switch (choice) {
            case 1 -> showAllProducts();
            case 2 -> searchProducts();
            case 3 -> showSalesReport();
            case 4 -> createBackorder();
            case 5 -> deleteOrder();
            default -> System.out.println("Неверная опция!");
        }
    }

    private void showAllProducts() {
        System.out.println("\n=== Все товары ===");
        productDAO.getAllAvailableProducts().forEach(System.out::println);
    }

    private void searchProducts() {
        System.out.println("\n=== Поиск товара ===");
        System.out.println("1. По названию");
        System.out.println("2. По дате");
        System.out.print("Выберите тип поиска: ");
        int searchType = readUserChoice();
        switch (searchType) {
            case 1 -> searchByName();
            case 2 -> searchByDate();
            default -> System.out.println("Неверный тип поиска!");
        }
    }

    private void searchByName() {
        System.out.print("Введите название товара: ");
        String name = scanner.nextLine();
        productDAO.getProductsByName(name).forEach(System.out::println);
    }

    private void searchByDate() {
        System.out.print("Введите дату (ГГГГ-ММ-ДД): ");
        String date = scanner.nextLine();
        productDAO.getProductsByDate(date).forEach(System.out::println);
    }

    private void showSalesReport() {
        try {
            List<Order> orders = orderDAO.getSalesReport();
            orders.forEach(System.out::println);
        } catch (SQLException e) {
            System.err.println("Ошибка получения отчета: " + e.getMessage());
        }
    }

    private void createBackorder() throws SQLException {
        System.out.println("\n=== Создание заказа ===");
        System.out.print("Введите название товара: ");
        String productName = scanner.nextLine();

        System.out.print("Введите количество: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // очистка буфера

        // Создаем объект Order и заполняем поля
        Order order = new Order();
        order.setProductName(productName);
        order.setQuantity(quantity);

        // Предположим, что дата заказа — текущая дата
        order.setOrderDate(new java.sql.Date(System.currentTimeMillis()));

        // Можно задать статус, например, "NEW"
        order.setStatus("NEW");

        // Вызываем метод из первого примера
        orderDAO.createOrder(order);

        System.out.println("Заказ успешно создан!");
    }


    private void deleteOrder() {
        System.out.println("\n=== Удаление заказа ===");
        System.out.print("Введите ID заказа: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();
        orderDAO.deleteOrder(orderId);
        System.out.println("Заказ успешно удален!");
    }
}
