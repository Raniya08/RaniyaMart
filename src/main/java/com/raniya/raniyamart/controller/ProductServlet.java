package com.raniya.raniyamart.controller;

import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String category = request.getParameter("category");

        List<Product> products;

        if ((keyword != null && !keyword.trim().isEmpty())
                || (category != null && !category.trim().isEmpty())) {

            products = productService.searchProducts(keyword, category);

        } else {

            products = productService.getAllProducts();
        }

        request.setAttribute("products", products);

        request.getRequestDispatcher("/products.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("create".equals(action)) {

            createProduct(request, response);

        } else if ("update".equals(action)) {

            updateProduct(request, response);

        } else if ("delete".equals(action)) {

            deleteProduct(request, response);

        } else {

            response.sendRedirect(
                    request.getContextPath() + "/products");
        }
    }

    private void createProduct(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Product product = new Product();

            product.setSellerId(
                    Integer.parseInt(
                            request.getParameter("sellerId")));

            product.setName(
                    request.getParameter("name"));

            product.setDescription(
                    request.getParameter("description"));

            product.setPrice(
                    new BigDecimal(
                            request.getParameter("price")));

            product.setStockQty(
                    Integer.parseInt(
                            request.getParameter("stockQty")));

            product.setCategory(
                    request.getParameter("category"));

            product.setImageUrl(
                    request.getParameter("imageUrl"));

            boolean created =
                    productService.createProduct(product);

            if (created) {
                response.sendRedirect(
                        request.getContextPath() + "/products");
            } else {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Unable to create product");
            }

        } catch (Exception e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid product data");
        }
    }

    private void updateProduct(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Product product = new Product();

            product.setId(
                    Integer.parseInt(
                            request.getParameter("id")));

            product.setName(
                    request.getParameter("name"));

            product.setDescription(
                    request.getParameter("description"));

            product.setPrice(
                    new BigDecimal(
                            request.getParameter("price")));

            product.setStockQty(
                    Integer.parseInt(
                            request.getParameter("stockQty")));

            product.setCategory(
                    request.getParameter("category"));

            product.setImageUrl(
                    request.getParameter("imageUrl"));

            boolean updated =
                    productService.updateProduct(product);

            if (updated) {
                response.sendRedirect(
                        request.getContextPath() + "/products");
            } else {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Unable to update product");
            }

        } catch (Exception e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid product data");
        }
    }

    private void deleteProduct(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            int id = Integer.parseInt(
                    request.getParameter("id"));

            boolean deleted =
                    productService.deleteProduct(id);

            if (deleted) {
                response.sendRedirect(
                        request.getContextPath() + "/products");
            } else {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Unable to delete product");
            }

        } catch (Exception e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid product id");
        }
    }
}
