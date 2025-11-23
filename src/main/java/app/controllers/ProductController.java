package app.controllers;

import app.dao.ProductDAO;
import app.models.Product;
import app.models.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProductController {

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> stockColumn;

    @FXML
    private Button addToCartButton;

    @FXML
    private Button viewCartButton;

    @FXML
    private Label infoLabel;

    private final ProductDAO productDAO = new ProductDAO();
    private User loggedInUser;

    @FXML
    public void initialize() {
        // Bind Product model fields to table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        loadProducts();
    }

    private void loadProducts() {
        ObservableList<Product> products = productDAO.findAll();
        productTable.setItems(products);
    }

    @FXML
    public void handleAddToCart() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            infoLabel.setText("Please select a product to add to the cart.");
            return;
        }

        // Add product to the global cart
        CartController.addProduct(selectedProduct);
        infoLabel.setText("✓ Added " + selectedProduct.getName() + " to cart");
    }

    @FXML
    public void handleViewCart() {
        try {
            // Pass user to cart controller
            if (loggedInUser != null) {
                CartController.setCurrentUser(loggedInUser);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cart.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) viewCartButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            infoLabel.setText("Error loading cart view.");
            e.printStackTrace();
        }
    }

    // Optional: if you want to show "Welcome, user"
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
        infoLabel.setText("Welcome " + user.getName() + "!");
    }
}
