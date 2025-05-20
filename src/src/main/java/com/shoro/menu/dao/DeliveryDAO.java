package com.shoro.menu.dao;

import com.shoro.menu.auth.Delivery; // Переместите класс Delivery в пакет model
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO {
    private final Connection connection;

    public DeliveryDAO(Connection connection) {
        this.connection = connection;
    }

    // Получение всех ожидающих доставок
    public List<Delivery> getPendingDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT * FROM deliveries WHERE status = 'PENDING'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                deliveries.add(mapDelivery(rs));
            }
        } catch (SQLException e) {
            handleException("Ошибка при получении ожидающих доставок", e);
        }
        return deliveries;
    }

    // Получение всех выполненных доставок
    public List<Delivery> getDeliveredOrders() {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT * FROM deliveries WHERE status = 'DELIVERED'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                deliveries.add(mapDelivery(rs));
            }
        } catch (SQLException e) {
            handleException("Ошибка при получении выполненных доставок", e);
        }
        return deliveries;
    }

    // Отметка доставки как выполненной
    public boolean markAsDelivered(int deliveryId) {
        String sql = "UPDATE deliveries SET status = 'DELIVERED', delivery_date = CURRENT_DATE WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, deliveryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleException("Ошибка при подтверждении доставки", e);
            return false;
        }
    }

    // Получение количества выполненных доставок
    public int getDeliveredCount() {
        String sql = "SELECT COUNT(*) FROM deliveries WHERE status = 'DELIVERED'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            handleException("Ошибка при подсчете доставок", e);
            return 0;
        }
    }

    // Расчет заработка доставщика
    public double calculateEarnings() {
        String sql = "SELECT SUM(delivery_fee) FROM deliveries WHERE status = 'DELIVERED'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.next() ? rs.getDouble(1) : 0.0;
        } catch (SQLException e) {
            handleException("Ошибка при расчете заработка", e);
            return 0.0;
        }
    }

    private Delivery mapDelivery(ResultSet rs) throws SQLException {
        return new Delivery(
                rs.getInt("id"),
                rs.getInt("order_id"),
                rs.getDate("delivery_date"),
                rs.getDouble("delivery_fee"),
                rs.getString("status")
        );
    }

    private void handleException(String message, SQLException e) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace();
        // Можно добавить логирование в файл
    }
}
