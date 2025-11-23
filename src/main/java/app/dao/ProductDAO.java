package app.dao;

import app.models.Product;
import app.db.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    
    public void insertProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (product_name, description, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.executeUpdate();
        }
    }

    public Product getProductById(int productId) throws SQLException {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt("product_id"), rs.getString("product_name"), 
                                  rs.getString("description"), rs.getDouble("price"), rs.getInt("quantity"));
            }
        }
        return null;
    }

    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(new Product(rs.getInt("product_id"), rs.getString("product_name"), 
                                        rs.getString("description"), rs.getDouble("price"), rs.getInt("quantity")));
            }
        }
        return products;
    }

    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET product_name = ?, description = ?, price = ?, quantity = ? WHERE product_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.setInt(5, product.getProductId());
            pstmt.executeUpdate();
        }
    }

    public void deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
        }
    }
}
