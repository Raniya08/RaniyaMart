package com.raniya.raniyamart.service.impl;

import com.raniya.raniyamart.dao.CartItemDAO;
import com.raniya.raniyamart.dao.OrderDAO;
import com.raniya.raniyamart.dao.impl.CartItemDAOImpl;
import com.raniya.raniyamart.dao.impl.OrderDAOImpl;
import com.raniya.raniyamart.exception.ValidationException;
import com.raniya.raniyamart.model.CartItem;
import com.raniya.raniyamart.model.Order;
import com.raniya.raniyamart.service.OrderService;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final CartItemDAO cartItemDAO;

    public OrderServiceImpl() {
        this.orderDAO = new OrderDAOImpl();
        this.cartItemDAO = new CartItemDAOImpl();
    }

    public OrderServiceImpl(OrderDAO orderDAO, CartItemDAO cartItemDAO) {
        this.orderDAO = orderDAO;
        this.cartItemDAO = cartItemDAO;
    }

    @Override
    public Order placeOrder(Long buyerId) {
        if (buyerId == null || buyerId <= 0) {
            throw new ValidationException("Invalid buyer ID.");
        }

        List<CartItem> cartItems = cartItemDAO.findByBuyerId(buyerId);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new ValidationException("Cannot place order: Cart is empty.");
        }

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            if (item.getProduct() == null) {
                throw new ValidationException("Cart contains invalid product reference.");
            }
            if (item.getProduct().getStockQty() < item.getQuantity()) {
                throw new ValidationException("Product '" + item.getProduct().getName() + "' does not have enough stock.");
            }
            total = total.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setTotalAmount(total);
        order.setStatus("CONFIRMED");

        return orderDAO.createOrder(order, cartItems);
    }

    @Override
    public List<Order> getBuyerOrders(Long buyerId) {
        if (buyerId == null || buyerId <= 0) throw new ValidationException("Invalid buyer ID.");
        return orderDAO.findByBuyerId(buyerId);
    }

    @Override
    public List<Order> getSellerOrders(Long sellerId) {
        if (sellerId == null || sellerId <= 0) throw new ValidationException("Invalid seller ID.");
        return orderDAO.findBySellerId(sellerId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    @Override
    public boolean updateOrderStatus(Long orderId, String status) {
        if (orderId == null || orderId <= 0) throw new ValidationException("Invalid order ID.");
        if (status == null || status.trim().isEmpty()) throw new ValidationException("Status cannot be empty.");
        return orderDAO.updateStatus(orderId, status.trim().toUpperCase());
    }
}
