package com.shoro.menu.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    // Убрали статическое соединение - теперь каждый вызов getConnection() создает новое соединение
    private static final Properties prop = loadProperties();

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Не найден файл db.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки db.properties", e);
        }
        return properties;
    }

    public static Connection getConnection() throws SQLException {
        // Всегда возвращаем новое соединение
        String url = prop.getProperty("db.url") +
                "?useSSL=false" +
                "&serverTimezone=UTC" +
                "&connectTimeout=5000";

        return DriverManager.getConnection(
                url,
                prop.getProperty("db.user"),
                prop.getProperty("db.password")
        );
    }

    // Метод для безопасного закрытия соединения
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
            }
        }
    }
}



