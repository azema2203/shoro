package com.shoro.menu.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // Создание нового заказа
    public boolean createOrder(int productId, int quantity) {
        String sql = "INSERT INTO orders (product_id, order_date, quantity) VALUES (?, CURRENT_DATE, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            stmt.setInt(2, quantity);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Получение всех заказов
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getDate("order_date"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Удаление заказа
    public boolean deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Получение количества заказов
    public int getOrderCount() {
        String sql = "SELECT COUNT(*) FROM orders";

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
}