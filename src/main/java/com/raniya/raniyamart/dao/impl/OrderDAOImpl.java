package com.raniya.raniyamart.dao.impl;

import com.raniya.raniyamart.dao.OrderDAO;
import com.raniya.raniyamart.model.CartItem;
import com.raniya.raniyamart.model.Order;
import com.raniya.raniyamart.model.OrderItem;
import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.util.DBUtil;
import com.raniya.raniyamart.exception.AppException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public Order createOrder(Order order, List<CartItem> cartItems) {
        String insertOrderSql = "INSERT INTO orders (buyer_id, total_amount, status) VALUES (?, ?, ?)";
        String insertItemSql = "INSERT INTO order_items (order_id, product_id, seller_id, quantity, price_per_unit) VALUES (?, ?, ?, ?, ?)";
        String updateStockSql = "UPDATE products SET stock_qty = stock_qty - ? WHERE id = ? AND stock_qty >= ?";
        String clearCartSql = "DELETE FROM cart_items WHERE buyer_id = ?";

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // Begin Transaction

            // 1. Insert Order
            try (PreparedStatement stmt = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, order.getBuyerId());
                stmt.setBigDecimal(2, order.getTotalAmount());
                stmt.setString(3, order.getStatus() != null ? order.getStatus() : "CONFIRMED");
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        order.setId(rs.getLong(1));
                    }
                }
            }

            // 2. Insert Order Items & Deduct Stock
            try (PreparedStatement itemStmt = conn.prepareStatement(insertItemSql);
                 PreparedStatement stockStmt = conn.prepareStatement(updateStockSql)) {

                for (CartItem ci : cartItems) {
                    Product p = ci.getProduct();
                    if (p.getStockQty() < ci.getQuantity()) {
                        throw new AppException("Insufficient stock for product: " + p.getName());
                    }

                    // Insert Item
                    itemStmt.setLong(1, order.getId());
                    itemStmt.setLong(2, ci.getProductId());
                    itemStmt.setLong(3, p.getSellerId());
                    itemStmt.setInt(4, ci.getQuantity());
                    itemStmt.setBigDecimal(5, p.getPrice());
                    itemStmt.executeUpdate();

                    // Update Stock
                    stockStmt.setInt(1, ci.getQuantity());
                    stockStmt.setLong(2, ci.getProductId());
                    stockStmt.setInt(3, ci.getQuantity());
                    int rowsUpdated = stockStmt.executeUpdate();
                    if (rowsUpdated == 0) {
                        throw new AppException("Stock update failed for product: " + p.getName());
                    }
                }
            }

            // 3. Clear Buyer Cart
            try (PreparedStatement clearStmt = conn.prepareStatement(clearCartSql)) {
                clearStmt.setLong(1, order.getBuyerId());
                clearStmt.executeUpdate();
            }

            conn.commit(); // Commit Transaction
            return order;

        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* log */ }
            }
            throw new AppException("Failed to place order: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { /* log */ }
            }
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        String orderSql = "SELECT id, buyer_id, total_amount, status, created_at FROM orders WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(orderSql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = mapResultSetToOrder(rs);
                    order.setItems(findOrderItems(conn, order.getId()));
                    return Optional.of(order);
                }
            }
        } catch (SQLException e) {
            throw new AppException("Error loading order by ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findByBuyerId(Long buyerId) {
        String sql = "SELECT id, buyer_id, total_amount, status, created_at FROM orders WHERE buyer_id = ? ORDER BY id DESC";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, buyerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order order = mapResultSetToOrder(rs);
                    order.setItems(findOrderItems(conn, order.getId()));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            throw new AppException("Error loading buyer orders: " + e.getMessage(), e);
        }
        return orders;
    }

    @Override
    public List<Order> findBySellerId(Long sellerId) {
        String sql = "SELECT DISTINCT o.id, o.buyer_id, o.total_amount, o.status, o.created_at " +
                "FROM orders o JOIN order_items oi ON o.id = oi.order_id " +
                "WHERE oi.seller_id = ? ORDER BY o.id DESC";

        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, sellerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order order = mapResultSetToOrder(rs);
                    order.setItems(findOrderItems(conn, order.getId()));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            throw new AppException("Error loading seller orders: " + e.getMessage(), e);
        }
        return orders;
    }

    @Override
    public List<Order> findAll() {
        String sql = "SELECT id, buyer_id, total_amount, status, created_at FROM orders ORDER BY id DESC";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = mapResultSetToOrder(rs);
                order.setItems(findOrderItems(conn, order.getId()));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new AppException("Error loading all orders: " + e.getMessage(), e);
        }
        return orders;
    }

    @Override
    public boolean updateStatus(Long orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setLong(2, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AppException("Error updating order status: " + e.getMessage(), e);
        }
    }

    private List<OrderItem> findOrderItems(Connection conn, Long orderId) throws SQLException {
        String sql = "SELECT oi.id, oi.order_id, oi.product_id, oi.seller_id, oi.quantity, oi.price_per_unit, oi.created_at, " +
                "p.name, p.image_url, p.category " +
                "FROM order_items oi JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";

        List<OrderItem> items = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem(
                            rs.getLong("id"),
                            rs.getLong("order_id"),
                            rs.getLong("product_id"),
                            rs.getLong("seller_id"),
                            rs.getInt("quantity"),
                            rs.getBigDecimal("price_per_unit"),
                            rs.getTimestamp("created_at")
                    );
                    Product p = new Product();
                    p.setId(rs.getLong("product_id"));
                    p.setName(rs.getString("name"));
                    p.setImageUrl(rs.getString("image_url"));
                    p.setCategory(rs.getString("category"));
                    item.setProduct(p);
                    items.add(item);
                }
            }
        }
        return items;
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        return new Order(
                rs.getLong("id"),
                rs.getLong("buyer_id"),
                rs.getBigDecimal("total_amount"),
                rs.getString("status"),
                rs.getTimestamp("created_at")
        );
    }
}
