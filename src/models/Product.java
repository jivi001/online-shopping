package models;

public class Product {
    private int productId;
    private String name;
    private double price;
    private String description;
    private int stock;

    public Product() {}

    public Product(int productId, String name, double price, String description, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    // getters & setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }