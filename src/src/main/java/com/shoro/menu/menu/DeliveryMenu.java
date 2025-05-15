package com.shoro.menu.menu;

import com.shoro.menu.auth.User;
import com.shoro.menu.db.DBConnection;
import com.shoro.menu.db.DeliveryDAO;
import com.shoro.menu.db.OrderDAO;
import com.shoro.menu.utils.ConsoleUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class DeliveryMenu {
    private final User user;
    private final Scanner scanner;
    private final DeliveryDAO deliveryDAO;
    private final OrderDAO orderDAO;

    public DeliveryMenu(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
        this.deliveryDAO = new DeliveryDAO();
        this.orderDAO = new OrderDAO();
    }

    public void showMenu() {
        System.out.println("Приветствую дорогой, Доставщик!");

        int choice;
        do {
            System.out.println("\nПожалуйста наберите номер меню для работы с программой, если закончили, то наберите 7:");
            System.out.println("1. Показать список товаров для доставки");
            System.out.println("2. Показать доставленные заказы");
            System.out.println("3. Доставить заказ");
            System.out.println("4. Показать количество доставленных товаров");
            System.out.println("5. Показать количество заказанных товаров");
            System.out.println("6. Показать мой заработок");
            System.out.println("7. Выход");

            choice = ConsoleUtils.readInt(scanner, "Выбор меню: ");

            switch (choice) {
                case 1:
                    showPendingDeliveries();
                    break;
                case 2:
                    showDeliveredOrders();
                    break;
                case 3:
                    deliverOrder();
                    break;
                case 4:
                    showDeliveredCount();
                    break;
                case 5:
                    showOrderedCount();
                    break;
                case 6:
                    showEarnings();
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        } while (choice != 7);
    }

    private void showPendingDeliveries() {
        System.out.println("\nСписок товаров для доставки:");
        deliveryDAO.getPendingDeliveries().forEach(System.out::println);
    }

    private void showDeliveredOrders() {
        System.out.println("\nДоставленные заказы:");
        deliveryDAO.getDeliveredOrders().forEach(System.out::println);
    }

    private void deliverOrder() {
        System.out.println("\nДоставить заказ:");
        int deliveryId = ConsoleUtils.readInt(scanner, "Введите ID доставки для отметки о доставке: ");

        if (deliveryDAO.markAsDelivered(deliveryId)) {
            System.out.println("Заказ успешно отмечен как доставленный.");
        } else {
            System.out.println("Не удалось отметить заказ как доставленный.");
        }
    }

    private void showDeliveredCount() {
        System.out.println("\nКоличество доставленных товаров: " + deliveryDAO.getDeliveredCount());
    }

    private void showOrderedCount() {
        System.out.println("\nКоличество заказанных товаров: " + orderDAO.getOrderCount());
    }

    private void showEarnings() {
        System.out.println("\nВаш заработок: $" + deliveryDAO.calculateEarnings());
    }
}
