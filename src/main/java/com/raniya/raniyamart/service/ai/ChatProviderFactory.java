package com.raniya.raniyamart.service.ai;

import java.io.InputStream;
import java.util.Properties;

/**
 * Factory for creating ChatProvider instances based on configuration.
 */
public class ChatProviderFactory {

    private static volatile ChatProvider instance;

    public static ChatProvider getProvider() {
        if (instance == null) {
            synchronized (ChatProviderFactory.class) {
                if (instance == null) {
                    instance = createProvider();
                }
            }
        }
        return instance;
    }

    private static ChatProvider createProvider() {
        Properties props = new Properties();
        try (InputStream is = ChatProviderFactory.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (Exception ignored) {}

        String providerType = System.getenv("AI_CHATBOT_PROVIDER");
        if (providerType == null || providerType.trim().isEmpty()) {
            providerType = props.getProperty("app.ai.chatbot.provider", props.getProperty("ai.chatbot.provider", "mock"));
        }

        if ("gemini".equalsIgnoreCase(providerType.trim())) {
            String apiKey = System.getenv("GEMINI_API_KEY");
            if (apiKey == null || apiKey.trim().isEmpty()) {
                apiKey = props.getProperty("app.ai.gemini.key", props.getProperty("gemini.api.key", ""));
            }
            return new GeminiChatProvider(apiKey);
        }

        return new MockChatProvider();
    }
}
