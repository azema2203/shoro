package com.shoro.menu.dao;

import com.shoro.menu.auth.Order;
import com.shoro.menu.db.DatabaseSetup;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
        private final Connection connection;

        public OrderDAO(Connection connection) {
            this.connection = connection;
        }

        // Создание заказа
        public void createOrder(Order order) throws SQLException {
            String sql = "INSERT INTO orders (product_name, quantity, order_date, status) VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, order.getProductName());
                stmt.setInt(2, order.getQuantity());
                stmt.setDate(3, order.getOrderDate());
                stmt.setString(4, order.getStatus());
                stmt.executeUpdate();
            }
        }

        // Получение отчета по продажам
        public List<Order> getSalesReport() throws SQLException {
            List<Order> orders = new ArrayList<>();
            String sql = "SELECT * FROM orders WHERE status = 'COMPLETED'";

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }
            return orders;
        }

        private Order mapOrder(ResultSet rs) throws SQLException {
            return new Order(
            );
        }


    // Удаление заказа
    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE id = ?";

        try (Connection conn = DatabaseSetup.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            handleException("Ошибка при удалении заказа", e);
        }
    }
        // Подсчет общего количества заказов
        public int getOrderCount() {
            String sql = "SELECT COUNT(*) FROM orders";

            try (Connection conn = DatabaseSetup.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                if (rs.next()) {
                    return rs.getInt(1);
                }

            } catch (SQLException e) {
                System.err.println("Ошибка при подсчете количества заказов: " + e.getMessage());
            }

            return 0;
        }

    private void handleException(String message, Exception e) {
        System.err.println(message);
        e.printStackTrace();
    }

}