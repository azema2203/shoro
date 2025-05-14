package com.shoro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/shoro_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // твой логин
    private static final String PASSWORD = "твой_пароль"; // твой пароль

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

