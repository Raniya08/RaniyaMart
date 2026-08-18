package com.raniya.raniyamart.dao;

import com.raniya.raniyamart.model.CartItem;
import com.raniya.raniyamart.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    Order createOrder(Order order, List<CartItem> cartItems);
    Optional<Order> findById(Long id);
    List<Order> findByBuyerId(Long buyerId);
    List<Order> findBySellerId(Long sellerId);
    List<Order> findAll();
    boolean updateStatus(Long orderId, String status);
}
