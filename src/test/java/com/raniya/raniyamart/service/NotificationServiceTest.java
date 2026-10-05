package com.raniya.raniyamart.service;

import com.raniya.raniyamart.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NotificationServiceTest {

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationServiceImpl();
    }

    @Test
    @DisplayName("Should send order confirmation notification cleanly")
    void testSendOrderConfirmationNotification() {
        assertDoesNotThrow(() -> notificationService.sendOrderConfirmationNotification(
            "buyer@example.com",
            "Jane Buyer",
            101L,
            new BigDecimal("4999.00")
        ));
    }

    @Test
    @DisplayName("Should send order status update notification cleanly")
    void testSendOrderStatusUpdateNotification() {
        assertDoesNotThrow(() -> notificationService.sendOrderStatusUpdateNotification(
            "buyer@example.com",
            101L,
            "SHIPPED"
        ));
    }
}
