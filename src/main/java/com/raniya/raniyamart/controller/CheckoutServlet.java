package com.raniya.raniyamart.controller;

import com.raniya.raniyamart.dto.UserResponseDTO;
import com.raniya.raniyamart.model.CartItem;
import com.raniya.raniyamart.model.Order;
import com.raniya.raniyamart.service.CartService;
import com.raniya.raniyamart.service.NotificationService;
import com.raniya.raniyamart.service.OrderService;
import com.raniya.raniyamart.service.impl.CartServiceImpl;
import com.raniya.raniyamart.service.impl.NotificationServiceImpl;
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
    private NotificationService notificationService;

    @Override
    public void init() throws ServletException {
        this.cartService = new CartServiceImpl();
        this.orderService = new OrderServiceImpl();
        this.notificationService = new NotificationServiceImpl();
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
            String method = req.getParameter("paymentMethod");
            String paymentLabel = "UPI / Online Payment";

            if ("UPI".equalsIgnoreCase(method)) {
                String upiApp = req.getParameter("upiApp");
                String upiId = req.getParameter("upiId");
                paymentLabel = "📱 UPI (" + (upiApp != null ? upiApp : "GPay") + ": " + (upiId != null && !upiId.isEmpty() ? upiId : "buyer@upi") + ")";
            } else if ("CARD".equalsIgnoreCase(method)) {
                String cardNumber = req.getParameter("cardNumber");
                String last4 = (cardNumber != null && cardNumber.length() >= 4) ? cardNumber.substring(cardNumber.length() - 4) : "8892";
                paymentLabel = "💳 Credit/Debit Card (Ending in *" + last4 + ")";
            } else if ("COD".equalsIgnoreCase(method)) {
                paymentLabel = "💵 Cash on Delivery (COD)";
            }

            Order order = orderService.placeOrder(user.getId());
            if (order != null) {
                order.setPaymentMethod(paymentLabel);
            }
            
            // Dispatch Order Placement Notification to Buyer (Isolate so notification errors don't interrupt order completion)
            try {
                if (notificationService != null && user != null && order != null) {
                    notificationService.sendOrderConfirmationNotification(
                        user.getEmail(),
                        user.getFullName(),
                        order.getId(),
                        order.getTotalAmount()
                    );
                }
            } catch (Exception notifEx) {
                System.err.println("Non-fatal notification error: " + notifEx.getMessage());
            }

            req.setAttribute("order", order);
            req.setAttribute("paymentLabel", paymentLabel);
            req.setAttribute("notificationSent", true);
            req.setAttribute("notificationMessage", "Order #" + (order != null ? order.getId() : "N/A") + " placed via " + paymentLabel + "! Confirmation alert sent to " + (user != null && user.getEmail() != null ? user.getEmail() : "buyer@raniyamart.com") + ".");
            req.getRequestDispatcher("/order-confirmation.jsp").forward(req, resp);
        } catch (Exception e) {
            System.err.println("Checkout execution failed: " + e.getMessage());
            e.printStackTrace();
            HttpSession session = req.getSession(true);
            session.setAttribute("flashError", "Checkout failed: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }

    private UserResponseDTO getCurrentUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return (session != null) ? (UserResponseDTO) session.getAttribute("currentUser") : null;
    }
}
