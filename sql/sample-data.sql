CREATE DATABASE IF NOT EXISTS amazonapp;
USE amazonapp;

CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE Products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE Order_Items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

INSERT INTO Products (name, price, description, stock) VALUES
('Logitech Mouse M235', 899.00, 'Wireless mouse with optical sensor', 50),
('HP Keyboard K150', 699.00, 'Full-sized keyboard with numeric pad', 30),
('Samsung Earbuds S23', 1999.00, 'High-quality bass and mic', 25),
('Boat Headphones 510', 1799.00, 'Wireless headphones with deep bass', 40);

INSERT INTO Users (username, password, email) VALUES
('testuser', '1234', 'test@example.com');
INSERT INTO Users (username, password, email) VALUES
('admin', 'adminpass', 'admin@example.com');