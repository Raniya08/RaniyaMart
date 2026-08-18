package com.raniya.raniyamart.dao;

import com.raniya.raniyamart.model.Review;
import java.util.List;

public interface ReviewDAO {
    Review create(Review review);
    List<Review> findByProductId(Long productId);
    double getAverageRating(Long productId);
}
