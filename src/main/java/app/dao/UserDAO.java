package app.dao;

import app.db.Database;
import app.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    // Simple password matching (in production, use bcrypt)
                    if (password.equals(storedPassword)) {
                        return new User(
                                rs.getInt("user_id"),
                                username,
                                rs.getString("email"),
                                storedPassword
                        );
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }
        return null;
    }

    public void save(User user) {
        String sql = "INSERT INTO Users (username, password, email) VALUES (?, ?, ?)";
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }
}
