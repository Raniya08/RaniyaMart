package com.raniya.raniyamart.service;

import com.raniya.raniyamart.model.Review;
import java.util.List;

public interface ReviewService {
    Review addReview(Long buyerId, Long productId, int rating, String comment);
    List<Review> getProductReviews(Long productId);
    double getAverageRating(Long productId);
}
