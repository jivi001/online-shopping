package app.controllers;

import app.dao.UserDAO;
import app.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private Label messageLabel;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        // you can add pre-load logic here
    }

    @FXML
    public void handleRegister() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        // Basic validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("All fields are required.");
            return;
        }

        // Create and save new user
        User user = new User(0, username, email, password);
        try {
            userDAO.save(user);
            messageLabel.setText("Registration successful! Redirecting...");
            navigateToLogin();
        } catch (Exception e) {
            messageLabel.setText("Failed to register user.");
            System.err.println("Registration error: " + e.getMessage());
        }
    }

    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            messageLabel.setText("Error loading login page.");
            System.err.println("Register → Login error: " + e.getMessage());
        }
    }
}
