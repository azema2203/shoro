package com.shoro.db;

import com.shoro.auth.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    // Реализация методов для работы с товарами
    public List<Product> getAllAvailableProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE status = 'AVAILABLE'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getDate("purchase_date"),
                        rs.getDate("delivery_date"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    // Другие методы (getProductsByName, getProductsByDate, addProduct и т.д.)
    // ...
}