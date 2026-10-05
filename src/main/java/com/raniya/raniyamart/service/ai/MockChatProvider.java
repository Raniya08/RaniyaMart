package com.raniya.raniyamart.service.ai;

import java.util.Locale;

/**
 * Enhanced Mock implementation of ChatProvider providing intelligent, multi-turn conversational responses.
 */
public class MockChatProvider implements ChatProvider {

    @Override
    public String chat(String userMessage, String sessionId) {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return "👋 Hello! I am RaniyaMart AI Assistant. How can I help you today?";
        }

        String msg = userMessage.trim().toLowerCase(Locale.ROOT);

        // Greetings & Introductions
        if (msg.matches(".*\\b(hi|hello|hey|greetings|good morning|good afternoon|good evening)\\b.*")) {
            return "👋 **Welcome to RaniyaMart!**\n" +
                   "I am your AI shopping assistant. I can help you with:\n" +
                   "• Browsing & Searching products (Electronics, Fashion, Furniture)\n" +
                   "• Order status tracking & Shipping timelines\n" +
                   "• Return, refund & cancellation policies\n" +
                   "• Payment options (UPI, Cards, COD)\n" +
                   "• Seller & Admin guidelines\n\n" +
                   "What would you like to know today?";
        }

        if (msg.contains("who are you") || msg.contains("what can you do") || msg.contains("your name") || msg.contains("about bot")) {
            return "🤖 **I am RaniyaMart AI Assistant!**\n" +
                   "I'm designed to give you instant 24/7 help with product browsing, order tracking, payment methods, returns, and account support across RaniyaMart.";
        }

        // Shipping & Delivery
        if (msg.contains("shipping") || msg.contains("delivery") || msg.contains("dispatch") || msg.contains("courier") || msg.contains("express")) {
            return "🚚 **Shipping & Delivery Guidelines:**\n" +
                   "• **Standard Delivery**: 2-5 business days across India.\n" +
                   "• **Free Shipping**: Available on all orders over ₹999.\n" +
                   "• **Express Delivery**: Select metro cities receive 24-48 hour dispatch.\n" +
                   "• **Tracking**: Live updates are available under your **Account -> Orders** page.";
        }

        // Order Status & Tracking
        if (msg.contains("track") || msg.contains("order status") || msg.contains("where is my order") || msg.contains("order history")) {
            return "📦 **Order Tracking & Status:**\n" +
                   "You can view and track your orders by visiting the **Orders** tab at the top navbar.\n" +
                   "Our order status workflow follows: `PENDING → CONFIRMED → SHIPPED → DELIVERED`.\n" +
                   "Sellers update tracking status in real time upon dispatch!";
        }

        // Returns, Refunds & Cancellations
        if (msg.contains("return") || msg.contains("refund") || msg.contains("cancel") || msg.contains("exchange") || msg.contains("damage")) {
            return "🔄 **Returns & Refund Policy:**\n" +
                   "• **7-Day Return Window**: Easy returns for unused items in original condition.\n" +
                   "• **Instant Refunds**: Refund is initiated to your original payment method (or UPI/Bank) within 3-5 business days after pickup.\n" +
                   "• **Cancellation**: You can cancel pending orders directly from your **Orders** dashboard before shipment.";
        }

        // Payment Methods & Currency
        if (msg.contains("payment") || msg.contains("pay") || msg.contains("upi") || msg.contains("card") || msg.contains("cod") || msg.contains("rupee") || msg.contains("price")) {
            return "💳 **Supported Payment Methods:**\n" +
                   "All prices on RaniyaMart are in **Indian Rupees (₹)**.\n" +
                   "• **UPI Payments**: Google Pay, PhonePe, Paytm, BHIM.\n" +
                   "• **Cards**: Visa, MasterCard, RuPay Debit & Credit Cards.\n" +
                   "• **Net Banking**: Supported across all major Indian banks.\n" +
                   "• **Cash on Delivery (COD)**: Available for orders up to ₹10,000.";
        }

        // Products & Categories
        if (msg.contains("product") || msg.contains("category") || msg.contains("electronics") || msg.contains("fashion") || msg.contains("furniture") || msg.contains("laptop") || msg.contains("phone") || msg.contains("headphone") || msg.contains("tv")) {
            return "🛍️ **Product Catalog Overview:**\n" +
                   "RaniyaMart offers premium curated items in:\n" +
                   "• **Electronics**: Laptops, 5G Smartphones, OLED Smart TVs, Noise-Canceling Headphones.\n" +
                   "• **Fashion**: Denim Jackets, Running Shoes, Leather Chronograph Watches.\n" +
                   "• **Furniture & Home**: Ergonomic Mesh Chairs, Solid Teak Study Tables, Smart HEPA Air Purifiers.\n" +
                   "Use the search bar on our homepage to filter products by category or keyword!";
        }

        // Reviews & Ratings
        if (msg.contains("review") || msg.contains("rating") || msg.contains("star") || msg.contains("feedback") || msg.contains("quality")) {
            return "⭐ **Product Reviews & 5-Star Ratings:**\n" +
                   "Verified buyers can rate products from **1 to 5 Stars** and leave written reviews on any product detail page. Average star ratings are calculated automatically!";
        }

        // Seller Information
        if (msg.contains("sell") || msg.contains("seller") || msg.contains("vendor") || msg.contains("listing") || msg.contains("stock")) {
            return "🏪 **Selling on RaniyaMart:**\n" +
                   "Sellers can register a **SELLER** account to access the **Seller Dashboard**. Features include:\n" +
                   "• Creating, editing, and managing product listings.\n" +
                   "• Updating inventory stock quantities.\n" +
                   "• Processing incoming buyer orders & updating shipment workflow status.";
        }

        // Admin Information
        if (msg.contains("admin") || msg.contains("moderation") || msg.contains("dashboard") || msg.contains("panel")) {
            return "⚙️ **Admin Panel & Moderation:**\n" +
                   "The platform Administrator manages system security, user account audits, global order monitoring, and catalog content moderation at `/admin/dashboard`. Admin credentials are pre-seeded in database setup.";
        }

        // Account & Login
        if (msg.contains("account") || msg.contains("login") || msg.contains("signup") || msg.contains("password") || msg.contains("register")) {
            return "👤 **Account & Security Help:**\n" +
                   "• **Register**: New users can sign up as **BUYER** or **SELLER** at `/register`.\n" +
                   "• **Security**: All account passwords are encrypted using salted **BCrypt** hashing.\n" +
                   "• **Login Issues**: If you forget your password or encounter access errors, verify your credentials or contact support.";
        }

        // Contact & Customer Support
        if (msg.contains("contact") || msg.contains("help") || msg.contains("support") || msg.contains("email") || msg.contains("phone")) {
            return "📞 **24/7 Customer Support:**\n" +
                   "• **Email**: support@raniyamart.com\n" +
                   "• **Toll-Free Phone**: 1800-123-RANIYA (1800-123-7264)\n" +
                   "• **Support Hours**: 24/7, 365 Days a Year.";
        }

        // Smart Contextual Fallback
        return "🤖 **RaniyaMart Assistant:**\n" +
               "I'm here to help you navigate RaniyaMart! You can ask me about:\n" +
               "• *'What are the shipping options?'*\n" +
               "• *'How do I track my active order?'*\n" +
               "• *'What is the return & refund policy?'*\n" +
               "• *'What payment methods are supported?'*\n" +
               "• *'How can I become a seller?'*";
    }
}
