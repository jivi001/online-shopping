package app.controllers;

import app.dao.UserDAO;
import app.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label messageLabel;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        // Initialize login screen
    }

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = userDAO.authenticate(username, password);
        if (user != null) {
            // Successful login, navigate to product list
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product_list.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                messageLabel.setText("Error loading application.");
                System.err.println("Error loading FXML: " + e.getMessage());
            }
        } else {
            // Failed login, show error message
            messageLabel.setText("Invalid username or password.");
        }
    }
}
