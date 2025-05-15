package com.shoro.menu.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO {

    // Получение всех ожидающих доставок
    public List<Delivery> getPendingDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT * FROM deliveries WHERE status = 'PENDING'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                deliveries.add(new Delivery(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getDate("delivery_date"),
                        rs.getDouble("delivery_fee"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveries;
    }

    // Получение всех выполненных доставок
    public List<Delivery> getDeliveredOrders() {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT * FROM deliveries WHERE status = 'DELIVERED'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                deliveries.add(new Delivery(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getDate("delivery_date"),
                        rs.getDouble("delivery_fee"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveries;
    }

    // Отметка доставки как выполненной
    public boolean markAsDelivered(int deliveryId) {
        String sql = "UPDATE deliveries SET status = 'DELIVERED', delivery_date = CURRENT_DATE WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, deliveryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Получение количества выполненных доставок
    public int getDeliveredCount() {
        String sql = "SELECT COUNT(*) FROM deliveries WHERE status = 'DELIVERED'";

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

    // Расчет заработка доставщика
    public double calculateEarnings() {
        String sql = "SELECT SUM(delivery_fee) FROM deliveries WHERE status = 'DELIVERED'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
