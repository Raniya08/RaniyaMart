package com.raniya.raniyamart.service.impl;

import com.raniya.raniyamart.service.NotificationService;
import java.math.BigDecimal;
import java.util.logging.Logger;

/**
 * Implementation of NotificationService for buyer order placement and status update alerts.
 */
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = Logger.getLogger(NotificationServiceImpl.class.getName());

    @Override
    public void sendOrderConfirmationNotification(String buyerEmail, String buyerName, Long orderId, BigDecimal totalAmount) {
        String msg = String.format(
            "🔔 [ORDER CONFIRMATION NOTIFICATION] Sent to %s (%s): Your RaniyaMart Order #%d for ₹%.2f has been successfully placed and confirmed!",
            buyerName, buyerEmail, orderId, totalAmount
        );
        LOGGER.info(msg);
        System.out.println(msg);
    }

    @Override
    public void sendOrderStatusUpdateNotification(String buyerEmail, Long orderId, String newStatus) {
        String msg = String.format(
            "🔔 [ORDER STATUS UPDATE NOTIFICATION] Sent to %s: Order #%d status updated to: %s.",
            buyerEmail, orderId, newStatus
        );
        LOGGER.info(msg);
        System.out.println(msg);
    }
}
