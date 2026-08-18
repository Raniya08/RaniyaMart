package com.raniya.raniyamart.controller;

import com.raniya.raniyamart.dto.UserResponseDTO;
import com.raniya.raniyamart.model.Order;
import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.service.OrderService;
import com.raniya.raniyamart.service.ProductService;
import com.raniya.raniyamart.service.impl.OrderServiceImpl;
import com.raniya.raniyamart.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(urlPatterns = {"/seller/dashboard", "/seller/product/add", "/seller/product/edit", "/seller/product/delete"})
public class SellerServlet extends HttpServlet {

    private ProductService productService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        this.productService = new ProductServiceImpl();
        this.orderService = new OrderServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserResponseDTO seller = getCurrentUser(req);
        if (seller == null || !"SELLER".equalsIgnoreCase(seller.getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String path = req.getServletPath();

        if ("/seller/product/edit".equals(path)) {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                try {
                    Long id = Long.parseLong(idStr);
                    Product product = productService.getProductById(id);
                    if (product.getSellerId().equals(seller.getId())) {
                        req.setAttribute("product", product);
                        req.getRequestDispatcher("/seller/product-form.jsp").forward(req, resp);
                        return;
                    }
                } catch (Exception e) {
                    req.getSession().setAttribute("flashError", "Product not found.");
                }
            }
            resp.sendRedirect(req.getContextPath() + "/seller/dashboard");
            return;
        }

        List<Product> products = productService.getSellerProducts(seller.getId());
        List<Order> incomingOrders = orderService.getSellerOrders(seller.getId());

        req.setAttribute("products", products);
        req.setAttribute("incomingOrders", incomingOrders);
        req.getRequestDispatcher("/seller/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserResponseDTO seller = getCurrentUser(req);
        if (seller == null || !"SELLER".equalsIgnoreCase(seller.getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String path = req.getServletPath();

        try {
            if ("/seller/product/add".equals(path)) {
                Product p = extractProductFromRequest(req, seller.getId());
                productService.createProduct(p);
                req.getSession().setAttribute("flashSuccess", "Product listing created successfully!");
            } else if ("/seller/product/edit".equals(path)) {
                Long productId = Long.parseLong(req.getParameter("productId"));
                Product p = extractProductFromRequest(req, seller.getId());
                p.setId(productId);
                productService.updateProduct(p);
                req.getSession().setAttribute("flashSuccess", "Listing updated successfully!");
            } else if ("/seller/product/delete".equals(path)) {
                Long productId = Long.parseLong(req.getParameter("productId"));
                productService.deleteProduct(productId);
                req.getSession().setAttribute("flashSuccess", "Listing removed successfully.");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("flashError", e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/seller/dashboard");
    }

    private Product extractProductFromRequest(HttpServletRequest req, Long sellerId) {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        int stockQty = Integer.parseInt(req.getParameter("stockQty"));
        String category = req.getParameter("category");
        String imageUrl = req.getParameter("imageUrl");

        Product p = new Product();
        p.setSellerId(sellerId);
        p.setName(name);
        p.setDescription(description);
        p.setPrice(price);
        p.setStockQty(stockQty);
        p.setCategory(category);
        p.setImageUrl(imageUrl);
        return p;
    }

    private UserResponseDTO getCurrentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (UserResponseDTO) session.getAttribute("currentUser") : null;
    }
}
