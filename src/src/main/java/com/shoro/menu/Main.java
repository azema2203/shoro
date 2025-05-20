package com.shoro.menu;

import com.shoro.menu.auth.*;
import com.shoro.menu.db.DatabaseSetup;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseSetup.initDatabase();
            Connection conn = DatabaseSetup.getConnection();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Роль (seller/delivery/provider): ");
            String role = scanner.nextLine();
            System.out.print("Логин: ");
            String username = scanner.nextLine();
            System.out.print("Пароль: ");
            String password = scanner.nextLine();


            User user = switch (role.toLowerCase()) {
                case "seller" -> new Seller(username, password, conn);
                case "delivery" -> new Deliver(username, password, conn);
                case "provider" -> new Provider(username, password, conn);
                default -> throw new IllegalArgumentException("Неизвестная роль");
            };

            user.showMenu();
        } catch (SQLException e) {
            System.err.println("Ошибка базы данных: " + e.getMessage());
        }
    }
}