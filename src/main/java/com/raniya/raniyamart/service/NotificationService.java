package com.raniya.raniyamart.service;

import java.math.BigDecimal;

/**
 * Service for sending order confirmation notifications to buyers.
 */
public interface NotificationService {

    /**
     * Send order placement confirmation notification to buyer.
     */
    void sendOrderConfirmationNotification(String buyerEmail, String buyerName, Long orderId, BigDecimal totalAmount);

    /**
     * Send status update notification to buyer when order status changes.
     */
    void sendOrderStatusUpdateNotification(String buyerEmail, Long orderId, String newStatus);
}
