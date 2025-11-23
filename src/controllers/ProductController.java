package app.controllers;

import dao.ProductDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Product;

public class ProductController {
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, Double> priceColumn;
    @FXML private TableColumn<Product, Integer> stockColumn;
    @FXML private Label infoLabel;

    private final ProductDAO productDAO = new ProductDAO();

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        priceColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getPrice()));
        stockColumn.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getStock()));

        ObservableList<Product> products = productDAO.findAll();
        productTable.setItems(products);
    }

    @FXML
    private void onAddToCart() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            infoLabel.setText("Select a product");
            return;
        }
        infoLabel.setText("Added to cart: " + selected.getName());
        // TODO: implement cart persistence
    }
}
