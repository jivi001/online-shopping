package app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    @SuppressWarnings("unused")
    private Button loginButton;

    @FXML
    public void initialize() {
        // Initialize login screen
    }

    @FXML
    public void handleLogin() {
        @SuppressWarnings("unused")
        String email = emailField.getText();
        @SuppressWarnings("unused")
        String password = passwordField.getText();
        // Add login logic here
    }
}
