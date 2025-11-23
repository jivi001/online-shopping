-- Enhanced Amazon App Database Schema
CREATE DATABASE IF NOT EXISTS amazonapp;
USE amazonapp;

-- Drop existing tables if they exist (for clean reinstall)
DROP TABLE IF EXISTS Order_Items;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Products;
DROP TABLE IF EXISTS Users;

-- Users Table with enhanced security and role management
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,  -- Store hashed passwords (bcrypt recommended)
    email VARCHAR(100) NOT NULL UNIQUE,
    role ENUM('customer', 'admin') DEFAULT 'customer',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    INDEX idx_email (email),
    INDEX idx_username (username)
);

-- Products Table with category and image support
CREATE TABLE Products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    category VARCHAR(50),
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    INDEX idx_category (category),
    INDEX idx_price (price),
    CHECK (price >= 0),
    CHECK (stock >= 0)
);

-- Orders Table with status tracking
CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(12, 2) NOT NULL,
    status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending',
    shipping_address TEXT,
    phone VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_order_date (order_date)
);

-- Order Items Table with price history
CREATE TABLE Order_Items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price_at_purchase DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE RESTRICT,
    INDEX idx_order_id (order_id),
    INDEX idx_product_id (product_id),
    CHECK (quantity > 0),
    CHECK (price_at_purchase >= 0)
);

-- ============================================
-- SAMPLE DATA
-- ============================================

-- Insert Users (Password should be hashed in production - these are plain text for demo only)
INSERT INTO Users (username, password, email, role) VALUES
('admin', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'admin@amazonapp.com', 'admin'),  -- Hash of 'adminpass'
('john_doe', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'john@example.com', 'customer'),
('jane_smith', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'jane@example.com', 'customer'),
('testuser', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'test@example.com', 'customer');

-- Insert Products with categories
INSERT INTO Products (name, price, description, stock, category, image_url) VALUES
-- Electronics - Peripherals
('Logitech Mouse M235', 899.00, 'Wireless mouse with optical sensor and long battery life', 50, 'Electronics', '/images/logitech-m235.jpg'),
('HP Keyboard K150', 699.00, 'Full-sized keyboard with numeric pad and spill-resistant design', 30, 'Electronics', '/images/hp-k150.jpg'),
('Dell Wireless Mouse', 1299.00, 'Ergonomic wireless mouse with precision tracking', 45, 'Electronics', '/images/dell-mouse.jpg'),
('Mechanical Gaming Keyboard RGB', 3499.00, 'RGB backlit mechanical keyboard with programmable keys', 20, 'Electronics', '/images/gaming-keyboard.jpg'),

-- Audio
('Samsung Earbuds S23', 1999.00, 'High-quality bass, noise cancellation, and built-in mic', 25, 'Audio', '/images/samsung-earbuds.jpg'),
('Boat Headphones 510', 1799.00, 'Wireless headphones with deep bass and 20-hour battery', 40, 'Audio', '/images/boat-510.jpg'),
('Sony WH-1000XM5', 24999.00, 'Premium noise-cancelling over-ear headphones', 15, 'Audio', '/images/sony-wh1000xm5.jpg'),
('JBL Flip 6 Speaker', 8999.00, 'Portable waterproof Bluetooth speaker with powerful sound', 35, 'Audio', '/images/jbl-flip6.jpg'),

-- Computers
('HP Pavilion Laptop', 54999.00, '15.6" FHD, Intel i5, 8GB RAM, 512GB SSD', 12, 'Computers', '/images/hp-pavilion.jpg'),
('Dell Inspiron Desktop', 42999.00, 'Intel i7, 16GB RAM, 1TB HDD, Windows 11', 8, 'Computers', '/images/dell-desktop.jpg'),
('MacBook Air M2', 114900.00, '13.6" Liquid Retina, 8GB RAM, 256GB SSD', 10, 'Computers', '/images/macbook-air.jpg'),

-- Accessories
('USB-C Hub 7-in-1', 1499.00, 'Multi-port adapter with HDMI, USB 3.0, SD card reader', 60, 'Accessories', '/images/usb-hub.jpg'),
('Laptop Stand Aluminum', 1299.00, 'Ergonomic adjustable laptop stand for better posture', 40, 'Accessories', '/images/laptop-stand.jpg'),
('Webcam 1080p HD', 2999.00, 'Full HD webcam with microphone and auto-focus', 25, 'Accessories', '/images/webcam-1080p.jpg'),

-- Mobile
('Samsung Galaxy S24', 79999.00, '6.2" AMOLED, 8GB RAM, 256GB storage, 5G', 18, 'Mobile', '/images/galaxy-s24.jpg'),
('iPhone 15 Pro', 134900.00, '6.1" Super Retina XDR, A17 Pro chip, 256GB', 10, 'Mobile', '/images/iphone-15-pro.jpg');

-- Insert Sample Orders
INSERT INTO Orders (user_id, total, status, shipping_address, phone) VALUES
(2, 2598.00, 'delivered', '123 Main St, Mumbai, MH 400001', '+91-9876543210'),
(3, 1999.00, 'shipped', '456 Park Ave, Bangalore, KA 560001', '+91-9876543211'),
(4, 5698.00, 'processing', '789 MG Road, Delhi, DL 110001', '+91-9876543212');

-- Insert Order Items
INSERT INTO Order_Items (order_id, product_id, quantity, price_at_purchase) VALUES
-- Order 1: john_doe
(1, 1, 1, 899.00),  -- Logitech Mouse
(1, 2, 1, 699.00),  -- HP Keyboard
(1, 5, 1, 1999.00), -- Samsung Earbuds (total adjusted for demo)

-- Order 2: jane_smith
(2, 5, 1, 1999.00), -- Samsung Earbuds

-- Order 3: testuser
(3, 1, 2, 899.00),  -- 2x Logitech Mouse
(3, 6, 2, 1799.00), -- 2x Boat Headphones
(3, 12, 1, 1499.00); -- USB Hub

-- ============================================
-- USEFUL QUERIES
-- ============================================

-- View all orders with user details
-- SELECT o.order_id, u.username, o.total, o.status, o.order_date 
-- FROM Orders o JOIN Users u ON o.user_id = u.user_id;

-- View order items with product details
-- SELECT oi.order_id, p.name, oi.quantity, oi.price_at_purchase, 
--        (oi.quantity * oi.price_at_purchase) as subtotal
-- FROM Order_Items oi JOIN Products p ON oi.product_id = p.product_id;

-- Check low stock products
-- SELECT name, stock FROM Products WHERE stock < 20 ORDER BY stock ASC;

-- User order history
-- SELECT u.username, COUNT(o.order_id) as total_orders, SUM(o.total) as total_spent
-- FROM Users u LEFT JOIN Orders o ON u.user_id = o.user_id
-- GROUP BY u.user_id;