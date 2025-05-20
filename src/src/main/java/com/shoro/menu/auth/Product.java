package com.shoro.menu.auth;

import java.sql.Date;

public class Product {
    private int id;
    private String name;
    private double price;
    private Date purchaseDate;
    private Date deliveryDate;
    private int quantity;
    private String status;

    // Конструктор
    public Product(int id, String name, double price, Date purchaseDate,
                   Date deliveryDate, int quantity, int minQuantity, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.quantity = quantity;
        this.status = status;
    }

    // Геттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public Date getPurchaseDate() { return purchaseDate; }
    public Date getDeliveryDate() { return deliveryDate; }
    public int getQuantity() { return quantity; }
    public String getStatus() { return status; }

    // Сеттеры (при необходимости)
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format(
                "Product[id=%d, name='%s', price=%.2f, quantity=%d, status='%s']",
                id, name, price, quantity, status
        );
    }
}
