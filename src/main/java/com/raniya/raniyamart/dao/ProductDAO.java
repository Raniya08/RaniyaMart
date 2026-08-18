package com.raniya.raniyamart.dao;

import com.raniya.raniyamart.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    Product create(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll(String category, String keyword, String sort);
    List<Product> findBySellerId(Long sellerId);
    boolean update(Product product);
    boolean delete(Long id);
}
