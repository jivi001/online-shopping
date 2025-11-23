package app.dao;

import app.db.Database;
import app.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    String storedPassword = rs.getString("password");

                    if (password.equals(storedPassword)) {
                        return new User(
                                rs.getInt("user_id"),
                                rs.getString("username"),
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

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }
}
