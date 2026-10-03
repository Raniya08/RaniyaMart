package com.raniya.raniyamart.service;

import com.raniya.raniyamart.service.ai.ChatProvider;
import com.raniya.raniyamart.service.ai.ChatProviderFactory;
import com.raniya.raniyamart.service.ai.MockChatProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatServiceTest {

    private ChatProvider chatProvider;

    @BeforeEach
    void setUp() {
        chatProvider = new MockChatProvider();
    }

    @Test
    @DisplayName("Should return shipping FAQ answer for shipping query")
    void testShippingQuery() {
        String response = chatProvider.chat("What are the shipping details?", "session-123");
        assertNotNull(response);
        assertTrue(response.contains("Shipping & Delivery Info"));
        assertTrue(response.contains("2-5 business days"));
    }

    @Test
    @DisplayName("Should return tracking FAQ answer for order tracking query")
    void testOrderTrackingQuery() {
        String response = chatProvider.chat("How do I track my order?", "session-123");
        assertNotNull(response);
        assertTrue(response.contains("Order Tracking"));
        assertTrue(response.contains("Pending → Confirmed → Shipped → Delivered"));
    }

    @Test
    @DisplayName("Should return return policy answer for return query")
    void testReturnQuery() {
        String response = chatProvider.chat("Can I return a defective product?", "session-123");
        assertNotNull(response);
        assertTrue(response.contains("Return & Refund Policy"));
    }

    @Test
    @DisplayName("Should return payment methods for payment query")
    void testPaymentQuery() {
        String response = chatProvider.chat("What payment options are supported?", "session-123");
        assertNotNull(response);
        assertTrue(response.contains("Payment Methods"));
        assertTrue(response.contains("UPI"));
    }

    @Test
    @DisplayName("Should initialize provider via ChatProviderFactory")
    void testFactoryInitialization() {
        ChatProvider provider = ChatProviderFactory.getProvider();
        assertNotNull(provider);
    }
}
