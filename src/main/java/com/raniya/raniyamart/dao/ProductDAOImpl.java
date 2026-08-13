package com.raniya.raniyamart.dao;

import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public boolean create(Product product) {

        String sql = """
                INSERT INTO products
                (seller_id, name, description, price, stock_qty, category, image_url)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, product.getSellerId());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setBigDecimal(4, product.getPrice());
            statement.setInt(5, product.getStockQty());
            statement.setString(6, product.getCategory());
            statement.setString(7, product.getImageUrl());

            int rows = statement.executeUpdate();

            if (rows == 0) {
                return false;
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    product.setId((int) keys.getLong(1));
                }
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Product findById(int id) {

        String sql = """
                SELECT id, seller_id, name, description,
                       price, stock_qty, category, image_url
                FROM products
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return mapProduct(result);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Product> findAll() {

        List<Product> products = new ArrayList<>();

        String sql = """
                SELECT id, seller_id, name, description,
                       price, stock_qty, category, image_url
                FROM products
                ORDER BY id DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                products.add(mapProduct(result));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> search(String keyword, String category) {

        List<Product> products = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
                SELECT id, seller_id, name, description,
                       price, stock_qty, category, image_url
                FROM products
                WHERE 1 = 1
                """);

        List<Object> parameters = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("""
                    AND (
                        LOWER(name) LIKE ?
                        OR LOWER(description) LIKE ?
                    )
                    """);

            String searchKeyword =
                    "%" + keyword.trim().toLowerCase() + "%";

            parameters.add(searchKeyword);
            parameters.add(searchKeyword);
        }

        if (category != null && !category.trim().isEmpty()) {
            sql.append(" AND category = ?");
            parameters.add(category.trim());
        }

        sql.append(" ORDER BY id DESC");

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    products.add(mapProduct(result));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public boolean update(Product product) {

        String sql = """
                UPDATE products
                SET name = ?,
                    description = ?,
                    price = ?,
                    stock_qty = ?,
                    category = ?,
                    image_url = ?
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setBigDecimal(3, product.getPrice());
            statement.setInt(4, product.getStockQty());
            statement.setString(5, product.getCategory());
            statement.setString(6, product.getImageUrl());
            statement.setInt(7, product.getId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {

        String sql = """
                DELETE FROM products
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Product mapProduct(ResultSet result) throws Exception {

        return new Product(
                result.getInt("id"),
                result.getInt("seller_id"),
                result.getString("name"),
                result.getString("description"),
                result.getBigDecimal("price"),
                result.getInt("stock_qty"),
                result.getString("category"),
                result.getString("image_url")
        );
    }
}