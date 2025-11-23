package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CartController {
    @FXML
    private ListView<?> cartListView;
    @FXML
    private Label totalLabel;
    @FXML
    private Button checkoutButton;

    @FXML
    public void initialize() {
        // Initialize cart screen
    }

    @FXML
    public void handleCheckout() {
        // Checkout logic
    }

    @FXML
    public void handleRemoveItem() {
        // Remove item from cart logic
    }
}
