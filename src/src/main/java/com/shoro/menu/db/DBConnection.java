package com.shoro.menu.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            String driver = prop.getProperty("db.driver");
            String url = prop.getProperty("db.url") +
                    "?useSSL=false" +
                    "&serverTimezone=UTC" +
                    "&connectTimeout=5000";
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");

            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

