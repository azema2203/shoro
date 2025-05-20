package com.shoro.menu.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;

    public class DatabaseSetup {

        private static final String URL = "jdbc:mysql://localhost:3306/shoro_company";
        private static final String USER = "atxzze";
        private static final String PASSWORD = "113362aB*";
        public static final String DB_NAME = "shoro_company";
        private static boolean silentMode = false;

            public static Connection getConnection() throws SQLException {
                return DriverManager.getConnection(URL, USER, PASSWORD);
            }

            public static void initDatabase() {
                initDatabase(true);
            }

            public static void initDatabase(boolean silent) {
                silentMode = silent;
                Connection rootConn = null;
                Statement rootStmt = null;

                try {
                    // Регистрация драйвера
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    // Создание базы данных
                    rootConn = DriverManager.getConnection(URL, USER, PASSWORD);
                    rootStmt = rootConn.createStatement();
                    rootStmt.executeUpdate("CREATE DATABASE IF NOT EXISTS shoro_company");

                    // Закрытие временных соединений
                    rootStmt.close();
                    rootConn.close();

                    // Создание таблиц
                    try (Connection dbConn = getConnection();
                         Statement dbStmt = dbConn.createStatement()) {

                        createUsersTable(dbStmt);
                        createProductsTable(dbStmt);
                        createOrdersTable(dbStmt);
                        createDeliveriesTable(dbStmt);
                        createDefaultUsers(dbStmt);

                        if (!silentMode) {
                            System.out.println("База данных успешно инициализирована.");
                        }
                    }

                } catch (ClassNotFoundException e) {
                    System.err.println("Ошибка: MySQL драйвер не найден.");
                    e.printStackTrace();
                } catch (SQLException e) {
                    System.err.println("Ошибка при работе с базой данных:");
                    e.printStackTrace();
                } finally {
                    try {
                        if (rootStmt != null) rootStmt.close();
                        if (rootConn != null) rootConn.close();
                    } catch (SQLException e) {
                        System.err.println("Ошибка при закрытии соединения:");
                        e.printStackTrace();
                    }
                }
            }

            private static void createUsersTable(Statement stmt) throws SQLException {
                String sql = "CREATE TABLE IF NOT EXISTS users ("
                        + "id INT PRIMARY KEY AUTO_INCREMENT,"
                        + "username VARCHAR(50) UNIQUE NOT NULL,"
                        + "password VARCHAR(50) NOT NULL,"
                        + "role ENUM('seller', 'delivery', 'supplier') NOT NULL)";
                stmt.executeUpdate(sql);
            }

            private static void createProductsTable(Statement stmt) throws SQLException {
                String sql = "CREATE TABLE IF NOT EXISTS products ("
                        + "id INT PRIMARY KEY AUTO_INCREMENT,"
                        + "name VARCHAR(255) NOT NULL,"
                        + "price DECIMAL(10,2),"
                        + "purchase_date DATE,"
                        + "delivery_date DATE)";
                stmt.executeUpdate(sql);
            }

            private static void createOrdersTable(Statement stmt) throws SQLException {
                String sql = "CREATE TABLE IF NOT EXISTS orders ("
                        + "id INT PRIMARY KEY AUTO_INCREMENT,"
                        + "product_name VARCHAR(255) NOT NULL,"
                        + "quantity INT NOT NULL,"
                        + "order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
                stmt.executeUpdate(sql);
            }

            private static void createDeliveriesTable(Statement stmt) throws SQLException {
                String sql = "CREATE TABLE IF NOT EXISTS deliveries ("
                        + "id INT PRIMARY KEY AUTO_INCREMENT,"
                        + "order_id INT NOT NULL,"
                        + "delivery_date DATE,"
                        + "status ENUM('pending', 'delivered') DEFAULT 'pending',"
                        + "FOREIGN KEY (order_id) REFERENCES orders(id))";
                stmt.executeUpdate(sql);
            }

            private static void createDefaultUsers(Statement stmt) throws SQLException {
                String sql = "INSERT IGNORE INTO users (username, password, role) VALUES "
                        + "('saleman', 'saleman123', 'seller'),"
                        + "('delivery', 'delivery123', 'delivery'),"
                        + "('provider', 'provider123', 'supplier')";
                stmt.executeUpdate(sql);
            }
        private static void createDebtsTable(Statement stmt) throws SQLException {
            String sql = "CREATE TABLE IF NOT EXISTS debts (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "order_id INT NOT NULL," +
                    "amount DECIMAL(10,2) NOT NULL," +
                    "due_date DATE NOT NULL," +
                    "FOREIGN KEY (order_id) REFERENCES orders(id)" +
                    ")";
            stmt.executeUpdate(sql);
        }

        private static void createDeliveryScheduleTable(Statement stmt) throws SQLException {
            String sql = "CREATE TABLE IF NOT EXISTS delivery_schedule (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "order_id INT NOT NULL," +
                    "planned_date DATE NOT NULL," +
                    "actual_date DATE," +
                    "FOREIGN KEY (order_id) REFERENCES orders(id)" +
                    ")";
            stmt.executeUpdate(sql);
        }
        }



