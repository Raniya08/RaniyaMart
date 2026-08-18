package com.raniya.raniyamart.controller.api;

import com.raniya.raniyamart.dto.ApiResponse;
import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.service.ProductService;
import com.raniya.raniyamart.service.impl.ProductServiceImpl;
import com.raniya.raniyamart.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/v1/products")
public class ProductApiController extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        this.productService = new ProductServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String category = req.getParameter("category");
        String keyword = req.getParameter("q");
        String sort = req.getParameter("sort");

        try {
            List<Product> products = productService.getCatalog(category, keyword, sort);
            JSONUtil.sendJsonResponse(resp, HttpServletResponse.SC_OK, ApiResponse.success(products));
        } catch (Exception e) {
            JSONUtil.sendJsonResponse(resp, HttpServletResponse.SC_BAD_REQUEST, ApiResponse.error(e.getMessage()));
        }
    }
}
