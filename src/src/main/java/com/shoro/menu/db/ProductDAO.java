package com.shoro.menu.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Получение всех доступных товаров
    public List<Product> getAllAvailableProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE status = 'AVAILABLE'";

        // Создаем новое соединение внутри метода
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении товаров: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    // Поиск товаров по названию
    public List<Product> getProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ? AND status = 'AVAILABLE'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Поиск товаров по дате закупки
    public List<Product> getProductsByDate(String date) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE purchase_date = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Добавление нового товара
    public boolean addProduct(Connection conn, String name, double price, String status, int quantity) throws SQLException {
        String sql = "INSERT INTO products (name, price, purchase_date, status, quantity) VALUES (?, ?, CURRENT_DATE, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setString(3, status);
            stmt.setInt(4, quantity);

            return stmt.executeUpdate() > 0;
        }
    }

    // Получение необходимых товаров (для поставщика)
    public List<Product> getNeededProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE status = 'NEEDED'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Получение количества необходимых товаров
    public int getNeededProductsCount() {
        String sql = "SELECT COUNT(*) FROM products WHERE status = 'NEEDED'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 1. Показать список товаров для поставки
    public List<Product> getProductsForSupply() {
        return getNeededProducts(); // Используем существующий метод
    }

    // 2. Показать количество поставляемого товара
    public int getTotalSupplyQuantity() {
        return getNeededProductsCount(); // Используем существующий метод
    }

    // 3. Товар с самым большим количеством заказов
    public Product getMostOrderedProduct() {
        String sql = """
            SELECT p.* FROM products p
            JOIN orders o ON p.id = o.product_id
            WHERE p.status = 'NEEDED'
            GROUP BY p.id
            ORDER BY SUM(o.quantity) DESC
            LIMIT 1""";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return mapProduct(rs);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске популярного товара: " + e.getMessage());
        }
        return null;
    }

    // 4. Товар с самым меньшим количеством заказов
    public Product getLeastOrderedProduct() {
        String sql = """
            SELECT p.* FROM products p
            JOIN orders o ON p.id = o.product_id
            WHERE p.status = 'NEEDED'
            GROUP BY p.id
            ORDER BY SUM(o.quantity) ASC
            LIMIT 1""";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return mapProduct(rs);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске непопулярного товара: " + e.getMessage());
        }
        return null;
    }

    // Единственный метод для маппинга ResultSet в Product
    private Product mapProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getDate("purchase_date"),
                rs.getDate("delivery_date"),
                rs.getInt("quantity"),
                rs.getString("status")
        );
    }

    // Метод для обновления статуса товара
    public boolean updateProductStatus(int productId, String newStatus) {
        String sql = "UPDATE products SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, productId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении статуса: " + e.getMessage());
            return false;
        }
    }
}