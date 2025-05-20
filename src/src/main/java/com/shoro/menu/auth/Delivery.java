package com.shoro.menu.auth;

import java.sql.Date;
import java.util.Objects;

public class Delivery {
    private int id;
    private int orderId;
    private Date deliveryDate;
    private double deliveryFee;
    private String status; // "PENDING", "IN_PROGRESS", "DELIVERED", "CANCELLED"
    private String deliveryAddress;
    private String courierName;

    // Конструкторы
    public Delivery(int id, int orderId, Date deliveryDate, double deliveryFee, String status) {}

    public Delivery(int id, int orderId, Date deliveryDate, double deliveryFee,
                    String status, String deliveryAddress, String courierName) {
        this.id = id;
        this.orderId = orderId;
        this.deliveryDate = deliveryDate;
        this.deliveryFee = deliveryFee;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.courierName = courierName;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    // Переопределение toString()
    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", deliveryDate=" + deliveryDate +
                ", deliveryFee=" + deliveryFee +
                ", status='" + status + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", courierName='" + courierName + '\'' +
                '}';
    }

    // Переопределение equals() и hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return id == delivery.id &&
                orderId == delivery.orderId &&
                Double.compare(delivery.deliveryFee, deliveryFee) == 0 &&
                Objects.equals(deliveryDate, delivery.deliveryDate) &&
                Objects.equals(status, delivery.status) &&
                Objects.equals(deliveryAddress, delivery.deliveryAddress) &&
                Objects.equals(courierName, delivery.courierName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, deliveryDate, deliveryFee, status, deliveryAddress, courierName);
    }
}
