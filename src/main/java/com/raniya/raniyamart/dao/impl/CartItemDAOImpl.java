package com.raniya.raniyamart.dao.impl;

import com.raniya.raniyamart.dao.CartItemDAO;
import com.raniya.raniyamart.model.CartItem;
import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.util.DBUtil;
import com.raniya.raniyamart.exception.AppException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAOImpl implements CartItemDAO {

    @Override
    public CartItem saveOrUpdate(CartItem item) {
        String checkSql = "SELECT id, quantity FROM cart_items WHERE buyer_id = ? AND product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setLong(1, item.getBuyerId());
            checkStmt.setLong(2, item.getProductId());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    Long existingId = rs.getLong("id");
                    int newQty = item.getQuantity();
                    String updateSql = "UPDATE cart_items SET quantity = ? WHERE id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, newQty);
                        updateStmt.setLong(2, existingId);
                        updateStmt.executeUpdate();
                    }
                    item.setId(existingId);
                    return item;
                }
            }

            String insertSql = "INSERT INTO cart_items (buyer_id, product_id, quantity) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setLong(1, item.getBuyerId());
                insertStmt.setLong(2, item.getProductId());
                insertStmt.setInt(3, item.getQuantity());
                insertStmt.executeUpdate();

                try (ResultSet rs = insertStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        item.setId(rs.getLong(1));
                    }
                }
            }
            return item;
        } catch (SQLException e) {
            throw new AppException("Error managing cart item: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CartItem> findByBuyerId(Long buyerId) {
        String sql = "SELECT c.id, c.buyer_id, c.product_id, c.quantity, c.created_at, " +
                "p.name, p.description, p.price, p.stock_qty, p.category, p.image_url, p.seller_id " +
                "FROM cart_items c JOIN products p ON c.product_id = p.id " +
                "WHERE c.buyer_id = ? ORDER BY c.id DESC";

        List<CartItem> items = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, buyerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CartItem item = new CartItem(
                            rs.getLong("id"),
                            rs.getLong("buyer_id"),
                            rs.getLong("product_id"),
                            rs.getInt("quantity"),
                            rs.getTimestamp("created_at")
                    );

                    Product product = new Product(
                            rs.getLong("product_id"),
                            rs.getLong("seller_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getBigDecimal("price"),
                            rs.getInt("stock_qty"),
                            rs.getString("category"),
                            rs.getString("image_url"),
                            null
                    );
                    item.setProduct(product);
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            throw new AppException("Error retrieving cart items: " + e.getMessage(), e);
        }
        return items;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM cart_items WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AppException("Error deleting cart item: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean clearCart(Long buyerId) {
        String sql = "DELETE FROM cart_items WHERE buyer_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, buyerId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AppException("Error clearing buyer cart: " + e.getMessage(), e);
        }
    }
}
