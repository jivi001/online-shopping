package app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;

public class ProductController {
    @FXML
    @SuppressWarnings("unused")
    private ListView<?> productListView;
    @FXML
    @SuppressWarnings("unused")
    private Button addToCartButton;

    @FXML
    public void initialize() {
        // Initialize product screen
    }

    @FXML
    public void handleAddToCart() {
        // Add product to cart logic
    }
}
