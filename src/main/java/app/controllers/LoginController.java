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
        // Optional: preload, animations, or focus logic
    }

    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter both username and password.");
            return;
        }

        User user = userDAO.authenticate(username, password);

        if (user != null) {
            loadProductList(user);
        } else {
            messageLabel.setText("Invalid username or password.");
        }
    }

    private void loadProductList(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product_list.fxml"));
            Parent root = loader.load();

            // Pass logged-in user into product page (optional but recommended)
            ProductController productController = loader.getController();
            productController.setLoggedInUser(user);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            messageLabel.setText("Error loading the product page.");
            System.err.println("FXML Load Error: " + e.getMessage());
        }
    }
}
