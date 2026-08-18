package com.raniya.raniyamart.controller;

import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.service.ProductService;
import com.raniya.raniyamart.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/products", "/product"})
public class ProductServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        this.productService = new ProductServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/product".equals(path)) {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                try {
                    Long id = Long.parseLong(idStr);
                    Product product = productService.getProductById(id);
                    req.setAttribute("product", product);
                    req.getRequestDispatcher("/product-detail.jsp").forward(req, resp);
                    return;
                } catch (Exception e) {
                    req.setAttribute("errorMessage", "Product not found.");
                }
            }
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        // Browse / Search / Sort Catalog
        String category = req.getParameter("category");
        String keyword = req.getParameter("q");
        String sort = req.getParameter("sort");

        List<Product> products = productService.getCatalog(category, keyword, sort);
        req.setAttribute("products", products);
        req.setAttribute("selectedCategory", category);
        req.setAttribute("searchKeyword", keyword);
        req.setAttribute("selectedSort", sort);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
