package dao;

import db.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {
    public ObservableList<Product> findAll() {
        ObservableList<Product> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Products ORDER BY created_at DESC";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("stock")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
