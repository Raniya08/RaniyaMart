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
        String safeName = (buyerName != null && !buyerName.trim().isEmpty()) ? buyerName : "Valued Buyer";
        String safeEmail = (buyerEmail != null && !buyerEmail.trim().isEmpty()) ? buyerEmail : "buyer@raniyamart.com";
        String safeOrderId = (orderId != null) ? String.valueOf(orderId) : "N/A";
        String safeAmount = (totalAmount != null) ? totalAmount.setScale(2, java.math.RoundingMode.HALF_UP).toString() : "0.00";

        String msg = String.format(
            "🔔 [ORDER CONFIRMATION NOTIFICATION] Sent to %s (%s): Your RaniyaMart Order #%s for ₹%s has been successfully placed and confirmed!",
            safeName, safeEmail, safeOrderId, safeAmount
        );
        LOGGER.info(msg);
        System.out.println(msg);
    }

    @Override
    public void sendOrderStatusUpdateNotification(String buyerEmail, Long orderId, String newStatus) {
        String safeEmail = (buyerEmail != null && !buyerEmail.trim().isEmpty()) ? buyerEmail : "buyer@raniyamart.com";
        String safeOrderId = (orderId != null) ? String.valueOf(orderId) : "N/A";
        String safeStatus = (newStatus != null) ? newStatus : "UPDATED";

        String msg = String.format(
            "🔔 [ORDER STATUS UPDATE NOTIFICATION] Sent to %s: Order #%s status updated to: %s.",
            safeEmail, safeOrderId, safeStatus
        );
        LOGGER.info(msg);
        System.out.println(msg);
    }
}
