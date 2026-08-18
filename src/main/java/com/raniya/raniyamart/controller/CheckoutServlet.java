package com.raniya.raniyamart.controller;

import com.raniya.raniyamart.dto.UserResponseDTO;
import com.raniya.raniyamart.model.CartItem;
import com.raniya.raniyamart.model.Order;
import com.raniya.raniyamart.service.CartService;
import com.raniya.raniyamart.service.OrderService;
import com.raniya.raniyamart.service.impl.CartServiceImpl;
import com.raniya.raniyamart.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        this.cartService = new CartServiceImpl();
        this.orderService = new OrderServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserResponseDTO user = getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<CartItem> cartItems = cartService.getCartItems(user.getId());
        if (cartItems.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        BigDecimal total = cartService.getCartTotal(user.getId());
        req.setAttribute("cartItems", cartItems);
        req.setAttribute("cartTotal", total);
        req.getRequestDispatcher("/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserResponseDTO user = getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            Order order = orderService.placeOrder(user.getId());
            req.setAttribute("order", order);
            req.getRequestDispatcher("/order-confirmation.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Checkout failed: " + e.getMessage());
            doGet(req, resp);
        }
    }

    private UserResponseDTO getCurrentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (UserResponseDTO) session.getAttribute("currentUser") : null;
    }
}
