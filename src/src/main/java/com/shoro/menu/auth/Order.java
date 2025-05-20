package com.shoro.menu.auth;

import java.sql.Date;

public class Order {
    private int id;
    private String productName;
    private int quantity;
    private Date orderDate;
    private String status;

    // Конструктор со всеми полями
    public Order() {
        this.id = id;
        this.productName = productName;
        setQuantity(quantity);
        this.orderDate = orderDate;
        setStatus(status);
    }

    // Конструктор без ID
    public Order(String productName, int quantity, Date orderDate, String status) {
        this();
    }


    // Геттеры
    public int getId() { return id; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public Date getOrderDate() { return orderDate; }
    public String getStatus() { return status; }

    // Сеттеры с валидацией
    public void setId(int id) { this.id = id; }

    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Название товара не может быть пустым");
        }
        this.productName = productName;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным числом");
        }
        this.quantity = quantity;
    }

    public void setOrderDate(Date orderDate) {
        if (orderDate == null) {
            throw new IllegalArgumentException("Дата заказа должна быть указана");
        }
        this.orderDate = orderDate;
    }

    public void setStatus(String status) {
        if (!status.matches("PENDING|PROCESSING|COMPLETED|CANCELLED")) {
            throw new IllegalArgumentException("Недопустимый статус заказа");
        }
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
                "Заказ #%d\nТовар: %s\nКоличество: %d\nДата заказа: %s\nСтатус: %s",
                id, productName, quantity, orderDate, status
        );
    }

    // Дополнительные методы
    public boolean isCompleted() {
        return "COMPLETED".equals(status);
    }

    public boolean canBeCancelled() {
        return !isCompleted() && !"CANCELLED".equals(status);
    }
}
