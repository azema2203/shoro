package com.shoro.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO {
    // Реализация методов для работы с доставками
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

    // Другие методы (getDeliveredOrders, markAsDelivered и т.д.)
    // ...
}
