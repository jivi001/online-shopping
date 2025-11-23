package app.dao;

import app.db.Database;
import app.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductDAO {

    public ObservableList<Product> findAll() {
        ObservableList<Product> list = FXCollections.observableArrayList();
        String sql = "SELECT product_id, name, description, price, stock FROM Products";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                );
                list.add(p);
            }

        } catch (Exception e) {
            System.err.println("Error fetching products: " + e.getMessage());
        }

        return list;
    }

    public void save(Product product) {
        String sql = "INSERT INTO Products (name, description, price, stock) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error saving product: " + e.getMessage());
        }
    }
}
