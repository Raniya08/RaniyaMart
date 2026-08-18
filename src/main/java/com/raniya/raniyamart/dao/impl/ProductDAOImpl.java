package com.raniya.raniyamart.dao.impl;

import com.raniya.raniyamart.dao.ProductDAO;
import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.util.DBUtil;
import com.raniya.raniyamart.exception.AppException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public Product create(Product product) {
        String sql = "INSERT INTO products (seller_id, name, description, price, stock_qty, category, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, product.getSellerId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setBigDecimal(4, product.getPrice());
            stmt.setInt(5, product.getStockQty());
            stmt.setString(6, product.getCategory());
            stmt.setString(7, product.getImageUrl());

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    product.setId(rs.getLong(1));
                }
            }
            return product;
        } catch (SQLException e) {
            throw new AppException("Database error while creating product: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT id, seller_id, name, description, price, stock_qty, category, image_url, created_at FROM products WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new AppException("Error finding product by ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll(String category, String keyword, String sort) {
        StringBuilder sql = new StringBuilder("SELECT id, seller_id, name, description, price, stock_qty, category, image_url, created_at FROM products WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (category != null && !category.trim().isEmpty() && !"all".equalsIgnoreCase(category)) {
            sql.append("AND LOWER(category) = LOWER(?) ");
            params.add(category.trim());
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (LOWER(name) LIKE ? OR LOWER(description) LIKE ?) ");
            String term = "%" + keyword.trim().toLowerCase() + "%";
            params.add(term);
            params.add(term);
        }

        if ("price_asc".equalsIgnoreCase(sort)) {
            sql.append("ORDER BY price ASC");
        } else if ("price_desc".equalsIgnoreCase(sort)) {
            sql.append("ORDER BY price DESC");
        } else {
            sql.append("ORDER BY id DESC");
        }

        List<Product> products = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new AppException("Error querying product catalog: " + e.getMessage(), e);
        }
        return products;
    }

    @Override
    public List<Product> findBySellerId(Long sellerId) {
        String sql = "SELECT id, seller_id, name, description, price, stock_qty, category, image_url, created_at FROM products WHERE seller_id = ? ORDER BY id DESC";
        List<Product> products = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, sellerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new AppException("Error querying products by seller ID: " + e.getMessage(), e);
        }
        return products;
    }

    @Override
    public boolean update(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock_qty = ?, category = ?, image_url = ? WHERE id = ? AND seller_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setInt(4, product.getStockQty());
            stmt.setString(5, product.getCategory());
            stmt.setString(6, product.getImageUrl());
            stmt.setLong(7, product.getId());
            stmt.setLong(8, product.getSellerId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AppException("Error updating product: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new AppException("Error deleting product: " + e.getMessage(), e);
        }
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getLong("seller_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getInt("stock_qty"),
                rs.getString("category"),
                rs.getString("image_url"),
                rs.getTimestamp("created_at")
        );
    }
}
