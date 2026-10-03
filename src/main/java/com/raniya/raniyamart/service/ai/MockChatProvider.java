package com.raniya.raniyamart.service.ai;

import java.util.Locale;

/**
 * Mock implementation of ChatProvider providing canned, high-quality domain FAQ responses.
 */
public class MockChatProvider implements ChatProvider {

    @Override
    public String chat(String userMessage, String sessionId) {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return "Hello! How can I assist you with RaniyaMart today?";
        }

        String msg = userMessage.trim().toLowerCase(Locale.ROOT);

        if (msg.contains("shipping") || msg.contains("delivery") || msg.contains("dispatch") || msg.contains("courier")) {
            return "🚚 **Shipping & Delivery Info:**\n" +
                   "• Standard delivery takes 2-5 business days across India.\n" +
                   "• Free shipping is available on all orders above ₹999.\n" +
                   "• Tracking updates are accessible under your Account -> Orders page.";
        }

        if (msg.contains("track") || msg.contains("order status") || msg.contains("where is my order")) {
            return "📦 **Order Tracking:**\n" +
                   "You can track your active orders by logging into your account and visiting the **Orders** tab. Status updates progress through: *Pending → Confirmed → Shipped → Delivered*.";
        }

        if (msg.contains("return") || msg.contains("refund") || msg.contains("cancel") || msg.contains("exchange")) {
            return "🔄 **Return & Refund Policy:**\n" +
                   "• 7-day hassle-free returns for unused items in original packaging.\n" +
                   "• Refunds are processed back to your original payment mode within 3-5 business days after inspection.";
        }

        if (msg.contains("payment") || msg.contains("pay") || msg.contains("upi") || msg.contains("card") || msg.contains("cod")) {
            return "💳 **Payment Methods:**\n" +
                   "RaniyaMart supports Credit/Debit Cards, Net Banking, UPI (Google Pay, PhonePe, Paytm), and Cash on Delivery (COD) for eligible pincodes.";
        }

        if (msg.contains("sell") || msg.contains("seller") || msg.contains("vendor") || msg.contains("listing")) {
            return "🏪 **Selling on RaniyaMart:**\n" +
                   "Register as a **SELLER** during sign-up to list your products, manage inventory stock, and process incoming buyer orders from your Seller Dashboard.";
        }

        if (msg.contains("product") || msg.contains("category") || msg.contains("electronics") || msg.contains("fashion") || msg.contains("furniture")) {
            return "🛍️ **Product Catalog:**\n" +
                   "RaniyaMart offers top products across Electronics, Fashion, and Furniture with authentic pricing in Indian Rupees (₹). Use our search bar to filter by category or keywords.";
        }

        if (msg.contains("account") || msg.contains("login") || msg.contains("password") || msg.contains("register")) {
            return "👤 **Account Support:**\n" +
                   "You can register as a **BUYER**, **SELLER**, or **ADMIN** at /register. If you face login issues, ensure your email and password match your registered credentials.";
        }

        if (msg.contains("contact") || msg.contains("help") || msg.contains("support") || msg.contains("email") || msg.contains("phone")) {
            return "📞 **Customer Support:**\n" +
                   "Reach out to our 24/7 support team at support@raniyamart.com or call toll-free 1800-123-RANIYA.";
        }

        return "🤖 **RaniyaMart Assistant:**\n" +
               "I'm here to help with orders, shipping, returns, payment options, and product inquiries. Try asking about 'shipping details', 'tracking my order', or 'return policy'!";
    }
}
