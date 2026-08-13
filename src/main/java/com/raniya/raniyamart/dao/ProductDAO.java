package com.raniya.raniyamart.dao;

import com.raniya.raniyamart.model.Product;

import java.util.List;

public interface ProductDAO {

    boolean create(Product product);

    Product findById(int id);

    List<Product> findAll();

    List<Product> search(String keyword, String category);

    boolean update(Product product);

    boolean delete(int id);
}