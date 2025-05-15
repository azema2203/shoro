package com.shoro.menu.auth;

import com.shoro.menu.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Authentication {
    private static final Scanner scanner = new Scanner(System.in);

    public static User authenticate() {
        System.out.println("Для запуска программы, пожалуйста введите тип аккаунта (seller/delivery/provider):");
        String accountType = scanner.nextLine().trim().toLowerCase();

        User.UserType userType;
        try {
            userType = User.UserType.valueOf(accountType.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Извините, но мы не нашли такой тип аккаунта, пожалуйста повторите.");
            System.exit(0);
            return null;
        }

        System.out.println("Введите логин:");
        String username = scanner.nextLine().trim();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine().trim();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND user_type = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, userType.toString());

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new User(username, password, userType);
            } else {
                System.out.println("Неверный логин или пароль.");
                System.exit(0);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}