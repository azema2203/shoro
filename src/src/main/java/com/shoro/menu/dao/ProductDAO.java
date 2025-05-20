
package com.shoro.menu.dao;

import com.shoro.menu.auth.Product;
import com.shoro.menu.db.DatabaseSetup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProductDAO {
    private static final String FILE_PATH = "sale.txt";
    private final Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }


    public List<Product> getAllAvailableProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseSetup.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapProduct(rs));
            }

        } catch (SQLException e) {
            handleException("Ошибка при получении товаров", e);
        }
        return products;
    }
        public List<Product> getProductsByName(String name) {
            List<Product> products = new ArrayList<>();
            String sql = "SELECT * FROM products WHERE name LIKE ?";

            try (Connection conn = DatabaseSetup.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, "%" + name + "%");
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    products.add(mapProduct(rs));
                }

            } catch (SQLException e) {
                handleException("Ошибка при поиске по названию", e);
            }
            return products;
        }


    private Product mapProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getDate("purchase_date"),
                rs.getDate("delivery_date"),
                rs.getInt("quantity"),
                rs.getInt("min_quantity"), rs.getString("status")
        );
    }




    // Поиск товаров по дате закупки
    public List<Product> getProductsByDate(String date) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE purchase_date = ?";

        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(mapProduct(rs));
            }

        } catch (SQLException | IllegalArgumentException e) {
            handleException("Ошибка при поиске по дате", e);
        }
        return products;
    }
    public List<Product> getNeededProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE quantity < min_quantity OR status = 'NEED_SUPPLY'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getDate("purchase_date"),
                        rs.getDate("delivery_date"),
                        rs.getInt("quantity"),
                        rs.getInt("min_quantity"), // Добавьте это поле в класс Product
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            handleException("Ошибка при получении списка для поставки", e);
        }
        return products;
    }
    public int getNeededProductsCount() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 6 && "NEEDED".equals(data[5])) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        return count;
    }
    public Product getMostOrderedProduct() {
        String sql = """
        SELECT p.* FROM products p
        JOIN orders o ON p.id = o.product_id
        WHERE p.status = 'NEEDED'
        GROUP BY p.id
        ORDER BY SUM(o.quantity) DESC
        LIMIT 1
    """;

        try (Connection conn = DatabaseSetup.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return mapProduct(rs);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при получении самого заказываемого товара: " + e.getMessage());
        }
        return null;
    }
    public Product getLeastOrderedProduct() {
        String sql = """
        SELECT p.*
        FROM products p
        JOIN orders o ON p.id = o.product_id
        WHERE p.status = 'NEEDED'
        GROUP BY p.id
        ORDER BY SUM(o.quantity) ASC
        LIMIT 1
    """;

        try (Connection conn = DatabaseSetup.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return mapProduct(rs);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при получении наименее заказываемого товара: " + e.getMessage());
        }

        return null;
    }

    private void handleException(String message, Exception e) {
        System.err.println(message);
        e.printStackTrace();
    }
}