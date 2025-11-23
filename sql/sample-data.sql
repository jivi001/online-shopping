USE amazonapp;

INSERT INTO Products (name, price, description, stock) VALUES
('Logitech Mouse M235', 899.00, 'Wireless mouse with optical sensor', 50),
('HP Keyboard K150', 699.00, 'Full-sized keyboard with numeric pad', 30),
('Samsung Earbuds S23', 1999.00, 'High-quality bass and mic', 25),
('Boat Headphones 510', 1799.00, 'Wireless headphones with deep bass', 40);

INSERT INTO Users (username, password, email) VALUES
('testuser', '1234', 'test@example.com');
INSERT INTO Users (username, password, email) VALUES
('admin', 'adminpass', 'admin@example.com');