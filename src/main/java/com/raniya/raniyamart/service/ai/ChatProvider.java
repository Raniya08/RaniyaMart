package com.raniya.raniyamart.service.ai;

/**
 * Interface for AI Chatbot Provider strategy.
 */
public interface ChatProvider {

    /**
     * Generate a response for a user's prompt in the context of RaniyaMart.
     *
     * @param userMessage Message sent by user
     * @param sessionId Current session identifier
     * @return Chatbot reply message
     */
    String chat(String userMessage, String sessionId);
}
