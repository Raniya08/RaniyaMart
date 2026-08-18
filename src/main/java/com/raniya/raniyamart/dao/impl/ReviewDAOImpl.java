package com.raniya.raniyamart.dao.impl;

import com.raniya.raniyamart.dao.ReviewDAO;
import com.raniya.raniyamart.model.Review;
import com.raniya.raniyamart.util.DBUtil;
import com.raniya.raniyamart.exception.AppException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements ReviewDAO {

    @Override
    public Review create(Review review) {
        String sql = "INSERT INTO reviews (buyer_id, product_id, order_id, rating, comment) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, review.getBuyerId());
            stmt.setLong(2, review.getProductId());
            stmt.setLong(3, review.getOrderId() != null ? review.getOrderId() : 1L);
            stmt.setInt(4, review.getRating());
            stmt.setString(5, review.getComment());

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    review.setId(rs.getLong(1));
                }
            }
            return review;
        } catch (SQLException e) {
            throw new AppException("Error creating review: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Review> findByProductId(Long productId) {
        String sql = "SELECT r.id, r.buyer_id, r.product_id, r.order_id, r.rating, r.comment, r.created_at, u.full_name " +
                "FROM reviews r JOIN users u ON r.buyer_id = u.id " +
                "WHERE r.product_id = ? ORDER BY r.id DESC";

        List<Review> reviews = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Review rev = new Review(
                            rs.getLong("id"),
                            rs.getLong("buyer_id"),
                            rs.getLong("product_id"),
                            rs.getLong("order_id"),
                            rs.getInt("rating"),
                            rs.getString("comment"),
                            rs.getTimestamp("created_at")
                    );
                    rev.setBuyerName(rs.getString("full_name"));
                    reviews.add(rev);
                }
            }
        } catch (SQLException e) {
            throw new AppException("Error querying product reviews: " + e.getMessage(), e);
        }
        return reviews;
    }

    @Override
    public double getAverageRating(Long productId) {
        String sql = "SELECT AVG(rating) FROM reviews WHERE product_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            throw new AppException("Error calculating rating average: " + e.getMessage(), e);
        }
        return 0.0;
    }
}
