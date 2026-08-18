package com.raniya.raniyamart.service.impl;

import com.raniya.raniyamart.dao.ReviewDAO;
import com.raniya.raniyamart.dao.impl.ReviewDAOImpl;
import com.raniya.raniyamart.exception.ValidationException;
import com.raniya.raniyamart.model.Review;
import com.raniya.raniyamart.service.ReviewService;
import com.raniya.raniyamart.util.ValidationUtil;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {

    private final ReviewDAO reviewDAO;

    public ReviewServiceImpl() {
        this.reviewDAO = new ReviewDAOImpl();
    }

    public ReviewServiceImpl(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    @Override
    public Review addReview(Long buyerId, Long productId, int rating, String comment) {
        if (buyerId == null || buyerId <= 0) throw new ValidationException("Invalid buyer ID.");
        if (productId == null || productId <= 0) throw new ValidationException("Invalid product ID.");
        if (rating < 1 || rating > 5) throw new ValidationException("Rating must be between 1 and 5 stars.");
        ValidationUtil.validateNotEmpty(comment, "Review comment");

        Review review = new Review();
        review.setBuyerId(buyerId);
        review.setProductId(productId);
        review.setRating(rating);
        review.setComment(ValidationUtil.sanitize(comment));

        return reviewDAO.create(review);
    }

    @Override
    public List<Review> getProductReviews(Long productId) {
        if (productId == null || productId <= 0) throw new ValidationException("Invalid product ID.");
        return reviewDAO.findByProductId(productId);
    }

    @Override
    public double getAverageRating(Long productId) {
        if (productId == null || productId <= 0) return 0.0;
        return reviewDAO.getAverageRating(productId);
    }
}
