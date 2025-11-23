package app.models;

import java.util.Date;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private List<OrderItem> orderItems;
    private double totalAmount;
    private Date orderDate;
    private String status;

    public Order(int orderId, int userId, List<OrderItem> orderItems, double totalAmount, Date orderDate, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderItems = orderItems;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
