package app.controllers;

import app.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class CartController {

    @FXML
    private ListView<Product> cartListView;
    @FXML
    private Label totalLabel;
    @FXML
    private Button checkoutButton;

    private static final ObservableList<Product> cartItems = FXCollections.observableArrayList();

    public static void addProduct(Product product) {
        cartItems.add(product);
    }

    @FXML
    public void initialize() {
        cartListView.setItems(cartItems);
        updateTotal();
    }

    @FXML
    public void handleCheckout() {
        // In a real app, you'd process the order here
        cartItems.clear();
        updateTotal();
        totalLabel.setText("Checkout successful!");
    }

    @FXML
    public void handleRemoveItem() {
        Product selectedItem = cartListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            cartItems.remove(selectedItem);
            updateTotal();
        }
    }

    private void updateTotal() {
        double total = 0;
        for (Product item : cartItems) {
            total += item.getPrice();
        }
        totalLabel.setText(String.format("Total: $%.2f", total));
    }
}
