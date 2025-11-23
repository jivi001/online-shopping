package app.controllers;

import app.models.Product;
import app.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.sql.*;

public class CartController {

    @FXML
    private ListView<Product> cartListView;

    @FXML
    private Label totalLabel;

    @FXML
    private Button checkoutButton;

    // Global cart shared across controllers
    private static final ObservableList<Product> cartItems = FXCollections.observableArrayList();
    private static User currentUser;

    public static void addProduct(Product product) {
        if (product != null) {
            cartItems.add(product);
        }
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    @FXML
    public void initialize() {
        cartListView.setItems(cartItems);

        // Custom cell factory for better display
        cartListView.setCellFactory(param -> new javafx.scene.control.ListCell<Product>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("🛍️ %s - ₹%.2f", item.getName(), item.getPrice()));
                }
            }
        });

        // Set placeholder for empty cart
        javafx.scene.control.Label placeholder = new javafx.scene.control.Label(
                "🛒 Your cart is empty\n\nAdd some products to get started!");
        placeholder.setStyle("-fx-text-fill: #888; -fx-font-size: 16px; -fx-text-alignment: center;");
        cartListView.setPlaceholder(placeholder);

        updateTotal();
    }

    @FXML
    public void handleCheckout() {
        if (cartItems.isEmpty()) {
            totalLabel.setText("Cart is empty.");
            return;
        }

        try {
            // Calculate total
            double total = cartItems.stream()
                    .mapToDouble(Product::getPrice)
                    .sum();

            // Get user ID (default to 1 if no user is logged in)
            int userId = (currentUser != null) ? currentUser.getUserId() : 1;

            System.out.println("=== CHECKOUT DEBUG ===");
            System.out.println("User ID: " + userId);
            System.out.println("Total: ₹" + total);
            System.out.println("Items in cart: " + cartItems.size());

            // Insert order into database - removed order_date from INSERT as it has DEFAULT
            // CURRENT_TIMESTAMP
            String orderSql = "INSERT INTO Orders (user_id, total, status) VALUES (?, ?, ?)";

            try (Connection conn = app.db.Database.getConnection();
                    PreparedStatement orderStmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {

                orderStmt.setInt(1, userId);
                orderStmt.setDouble(2, total);
                orderStmt.setString(3, "pending");

                int rowsAffected = orderStmt.executeUpdate();
                System.out.println("Order insert rows affected: " + rowsAffected);

                // Get the generated order ID
                ResultSet rs = orderStmt.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);
                    System.out.println("Generated Order ID: " + orderId);

                    // Insert order items
                    String itemSql = "INSERT INTO Order_Items (order_id, product_id, quantity, price_at_purchase) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement itemStmt = conn.prepareStatement(itemSql)) {
                        for (Product product : cartItems) {
                            System.out.println("Adding item: " + product.getName() + " (ID: " + product.getProductId()
                                    + ") - ₹" + product.getPrice());
                            itemStmt.setInt(1, orderId);
                            itemStmt.setInt(2, product.getProductId());
                            itemStmt.setInt(3, 1); // Default quantity of 1
                            itemStmt.setDouble(4, product.getPrice());
                            itemStmt.addBatch();
                        }
                        int[] itemResults = itemStmt.executeBatch();
                        System.out.println("Order items inserted: " + itemResults.length);
                    }

                    System.out.println("=== CHECKOUT SUCCESS ===");
                    totalLabel.setText("✓ Order #" + orderId + " placed successfully!");
                } else {
                    System.err.println("ERROR: Could not get generated order ID");
                    totalLabel.setText("Error: Could not create order");
                }
            }

            cartItems.clear();
            updateTotal();

        } catch (Exception e) {
            System.err.println("=== CHECKOUT ERROR ===");
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
            totalLabel.setText("Checkout failed: " + e.getMessage());
        }
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
        double total = cartItems.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        totalLabel.setText(String.format("Total: ₹%.2f", total));
    }
}
