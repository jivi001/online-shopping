package app.controllers;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;
    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void onLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User u = userDAO.authenticate(username, password);
        if (u != null) {
            // load products view
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/product_list.fxml"));
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) { e.printStackTrace(); }
        } else {
            messageLabel.setText("Invalid credentials");
        }
    }
}
