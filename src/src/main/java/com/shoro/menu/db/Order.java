package com.shoro.menu.db;

import java.sql.Date;
import java.util.Objects;

public class Order {
    private int id;
    private int productId;
    private Date orderDate;
    private int quantity;
    private String status; // например: "PENDING", "COMPLETED", "CANCELLED"

    // Конструктор
    public Order(int id, int productId, Date orderDate, int quantity) {
        this.id = id;
        this.productId = productId;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.status = status;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Переопределение toString() для удобного вывода
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productId=" + productId +
                ", orderDate=" + orderDate +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                '}';
    }

    // Переопределение equals() и hashCode() для корректной работы с коллекциями
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                productId == order.productId &&
                quantity == order.quantity &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, orderDate, quantity, status);
    }
}
