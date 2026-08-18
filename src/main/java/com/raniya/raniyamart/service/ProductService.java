package com.raniya.raniyamart.service;

import com.raniya.raniyamart.model.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product getProductById(Long id);
    List<Product> getCatalog(String category, String keyword, String sort);
    List<Product> getSellerProducts(Long sellerId);
    boolean updateProduct(Product product);
    boolean deleteProduct(Long id);
}
