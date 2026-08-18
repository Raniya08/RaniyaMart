package com.raniya.raniyamart.controller;

import com.raniya.raniyamart.dto.UserResponseDTO;
import com.raniya.raniyamart.model.CartItem;
import com.raniya.raniyamart.service.CartService;
import com.raniya.raniyamart.service.impl.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(urlPatterns = {"/cart", "/cart/add", "/cart/update", "/cart/remove"})
public class CartServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() throws ServletException {
        this.cartService = new CartServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserResponseDTO user = getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<CartItem> cartItems = cartService.getCartItems(user.getId());
        BigDecimal total = cartService.getCartTotal(user.getId());

        req.setAttribute("cartItems", cartItems);
        req.setAttribute("cartTotal", total);
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserResponseDTO user = getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();

        try {
            if ("/cart/add".equals(path)) {
                Long productId = Long.parseLong(req.getParameter("productId"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                cartService.addToCart(user.getId(), productId, quantity);
                req.getSession().setAttribute("flashSuccess", "Item added to cart!");
            } else if ("/cart/update".equals(path)) {
                Long itemId = Long.parseLong(req.getParameter("itemId"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                cartService.updateQuantity(itemId, quantity);
            } else if ("/cart/remove".equals(path)) {
                Long itemId = Long.parseLong(req.getParameter("itemId"));
                cartService.removeFromCart(itemId);
                req.getSession().setAttribute("flashSuccess", "Item removed from cart.");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("flashError", e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private UserResponseDTO getCurrentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (UserResponseDTO) session.getAttribute("currentUser") : null;
    }
}
