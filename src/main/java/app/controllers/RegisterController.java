package app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    @SuppressWarnings("unused")
    private Button registerButton;

    @FXML
    public void initialize() {
        // Initialize registration screen
    }

    @FXML
    public void handleRegister() {
        @SuppressWarnings("unused")
        String name = nameField.getText();
        @SuppressWarnings("unused")
        String email = emailField.getText();
        @SuppressWarnings("unused")
        String password = passwordField.getText();
        // Add registration logic here
    }
}
