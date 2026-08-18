package com.raniya.raniyamart.service.impl;

import com.raniya.raniyamart.dao.ProductDAO;
import com.raniya.raniyamart.dao.impl.ProductDAOImpl;
import com.raniya.raniyamart.exception.ResourceNotFoundException;
import com.raniya.raniyamart.exception.ValidationException;
import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.service.ProductService;
import com.raniya.raniyamart.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    public ProductServiceImpl() {
        this.productDAO = new ProductDAOImpl();
    }

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public Product createProduct(Product product) {
        validateProduct(product);
        return productDAO.create(product);
    }

    @Override
    public Product getProductById(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("Invalid product ID.");
        }
        return productDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

    @Override
    public List<Product> getCatalog(String category, String keyword, String sort) {
        return productDAO.findAll(category, keyword, sort);
    }

    @Override
    public List<Product> getSellerProducts(Long sellerId) {
        if (sellerId == null || sellerId <= 0) {
            throw new ValidationException("Invalid seller ID.");
        }
        return productDAO.findBySellerId(sellerId);
    }

    @Override
    public boolean updateProduct(Product product) {
        if (product.getId() == null || product.getId() <= 0) {
            throw new ValidationException("Product ID is required for update.");
        }
        validateProduct(product);
        return productDAO.update(product);
    }

    @Override
    public boolean deleteProduct(Long id) {
        if (id == null || id <= 0) {
            throw new ValidationException("Invalid product ID.");
        }
        return productDAO.delete(id);
    }

    private void validateProduct(Product product) {
        if (product == null) {
            throw new ValidationException("Product data is missing.");
        }
        ValidationUtil.validateNotEmpty(product.getName(), "Product Name");
        ValidationUtil.validateNotEmpty(product.getCategory(), "Category");
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Price must be greater than $0.00.");
        }
        if (product.getStockQty() == null || product.getStockQty() < 0) {
            throw new ValidationException("Stock quantity cannot be negative.");
        }
    }
}
