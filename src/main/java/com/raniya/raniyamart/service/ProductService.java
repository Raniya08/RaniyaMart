package com.raniya.raniyamart.service;

import com.raniya.raniyamart.dao.ProductDAO;
import com.raniya.raniyamart.dao.ProductDAOImpl;
import com.raniya.raniyamart.model.Product;

import java.util.List;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAOImpl();
    }

    public boolean createProduct(Product product) {
        if (product == null) {
            return false;
        }

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            return false;
        }

        if (product.getPrice() == null || product.getPrice().signum() < 0) {
            return false;
        }

        if (product.getStockQty() < 0) {
            return false;
        }

        return productDAO.create(product);
    }

    public Product getProduct(int id) {
        return productDAO.findById(id);
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public List<Product> searchProducts(String keyword, String category) {
        return productDAO.search(keyword, category);
    }

    public boolean updateProduct(Product product) {
        if (product == null || product.getId() <= 0) {
            return false;
        }

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            return false;
        }

        if (product.getPrice() == null || product.getPrice().signum() < 0) {
            return false;
        }

        if (product.getStockQty() < 0) {
            return false;
        }

        return productDAO.update(product);
    }

    public boolean deleteProduct(int id) {
        if (id <= 0) {
            return false;
        }

        return productDAO.delete(id);
    }
}
