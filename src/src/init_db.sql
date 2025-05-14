-- Создание базы данных
CREATE DATABASE IF NOT EXISTS shoro_company;
USE shoro_company;

-- Таблица пользователей
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    user_type ENUM('SELLER', 'DELIVERY', 'PROVIDER') NOT NULL
    );

-- Таблица товаров
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    purchase_date DATE NOT NULL,
    delivery_date DATE,
    quantity INT NOT NULL DEFAULT 1,
    status ENUM('AVAILABLE', 'SOLD', 'DELIVERED', 'NEEDED') NOT NULL DEFAULT 'AVAILABLE'
    );

-- Таблица заказов
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    order_date DATE NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id)
    );

-- Таблица доставок
CREATE TABLE IF NOT EXISTS deliveries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    delivery_date DATE,
    delivery_fee DECIMAL(10, 2) NOT NULL DEFAULT 5.00,
    status ENUM('PENDING', 'DELIVERED') NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (order_id) REFERENCES orders(id)
    );

-- Вставка тестовых пользователей
INSERT INTO users (username, password, user_type) VALUES
     ('saleman', 'pa$$w0rd123', 'SELLER'),
     ('delivery', 'delivery123', 'DELIVERY'),
     ('provider', 'provider123', 'PROVIDER');

-- Вставка тестовых товаров
INSERT INTO products (name, price, purchase_date, delivery_date, quantity, status) VALUES
      ('Молоток', 15.99, '2023-01-10', '2023-01-15', 50, 'AVAILABLE'),
      ('Гвозди', 5.50, '2023-01-12', '2023-01-17', 200, 'AVAILABLE'),
      ('Дрель', 89.99, '2023-02-01', '2023-02-05', 15, 'AVAILABLE'),
      ('Пила', 45.00, '2023-02-10', '2023-02-15', 20, 'AVAILABLE');