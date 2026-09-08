package com.raniya.raniyamart.controller;

import com.raniya.raniyamart.dto.UserResponseDTO;
import com.raniya.raniyamart.model.Order;
import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.service.OrderService;
import com.raniya.raniyamart.service.ProductService;
import com.raniya.raniyamart.service.UserService;
import com.raniya.raniyamart.service.impl.OrderServiceImpl;
import com.raniya.raniyamart.service.impl.ProductServiceImpl;
import com.raniya.raniyamart.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/dashboard", "/admin/product/delete"})
public class AdminServlet extends HttpServlet {

    private UserService userService;
    private OrderService orderService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserServiceImpl();
        this.orderService = new OrderServiceImpl();
        this.productService = new ProductServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserResponseDTO admin = getCurrentUser(req);
        if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        List<UserResponseDTO> users = userService.getAllUsers();
        List<Order> orders = orderService.getAllOrders();
        List<Product> products = productService.getCatalog("all", null, "newest");

        req.setAttribute("users", users);
        req.setAttribute("orders", orders);
        req.setAttribute("products", products);
        req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserResponseDTO admin = getCurrentUser(req);
        if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String path = req.getServletPath();
        try {
            if ("/admin/product/delete".equals(path)) {
                Long productId = Long.parseLong(req.getParameter("productId"));
                productService.deleteProduct(productId);
                req.getSession().setAttribute("flashSuccess", "Admin moderated & removed product #" + productId + " successfully.");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("flashError", e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
    }

    private UserResponseDTO getCurrentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (UserResponseDTO) session.getAttribute("currentUser") : null;
    }
}
