package com.shoro.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    // Реализация методов для работы с заказами
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

    // Другие методы (deleteOrder, getOrderCount и т.д.)
    // ...
}
