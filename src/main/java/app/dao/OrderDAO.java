package app.dao;

import app.models.Order;
import app.models.OrderItem;
import app.db.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    
    public void insertOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (user_id, total_amount, order_date, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, order.getUserId());
            pstmt.setDouble(2, order.getTotalAmount());
            pstmt.setTimestamp(3, new Timestamp(order.getOrderDate().getTime()));
            pstmt.setString(4, order.getStatus());
            pstmt.executeUpdate();
        }
    }

    public Order getOrderById(int orderId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                List<OrderItem> orderItems = getOrderItems(orderId);
                return new Order(rs.getInt("order_id"), rs.getInt("user_id"), orderItems, 
                               rs.getDouble("total_amount"), rs.getTimestamp("order_date"), rs.getString("status"));
            }
        }
        return null;
    }

    public List<Order> getOrdersByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                List<OrderItem> orderItems = getOrderItems(orderId);
                orders.add(new Order(orderId, rs.getInt("user_id"), orderItems, 
                                    rs.getDouble("total_amount"), rs.getTimestamp("order_date"), rs.getString("status")));
            }
        }
        return orders;
    }

    public void updateOrder(Order order) throws SQLException {
        String sql = "UPDATE orders SET total_amount = ?, status = ? WHERE order_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, order.getTotalAmount());
            pstmt.setString(2, order.getStatus());
            pstmt.setInt(3, order.getOrderId());
            pstmt.executeUpdate();
        }
    }

    public void deleteOrder(int orderId) throws SQLException {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();
        }
    }

    private List<OrderItem> getOrderItems(int orderId) throws SQLException {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        List<OrderItem> orderItems = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                orderItems.add(new OrderItem(rs.getInt("order_item_id"), rs.getInt("order_id"), 
                                            rs.getInt("product_id"), rs.getInt("quantity"), rs.getDouble("price")));
            }
        }
        return orderItems;
    }
}
