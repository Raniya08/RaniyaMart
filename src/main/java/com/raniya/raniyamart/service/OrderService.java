package com.raniya.raniyamart.service;

import com.raniya.raniyamart.model.Order;
import java.util.List;

public interface OrderService {
    Order placeOrder(Long buyerId);
    List<Order> getBuyerOrders(Long buyerId);
    List<Order> getSellerOrders(Long sellerId);
    List<Order> getAllOrders();
    boolean updateOrderStatus(Long orderId, String status);
}
