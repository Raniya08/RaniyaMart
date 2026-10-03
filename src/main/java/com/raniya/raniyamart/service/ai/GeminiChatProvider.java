package com.raniya.raniyamart.service.ai;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Gemini API implementation of ChatProvider with degraded fallback to MockChatProvider on failure.
 */
public class GeminiChatProvider implements ChatProvider {

    private final String apiKey;
    private final MockChatProvider fallbackProvider;

    public GeminiChatProvider(String apiKey) {
        this.apiKey = apiKey;
        this.fallbackProvider = new MockChatProvider();
    }

    @Override
    public String chat(String userMessage, String sessionId) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            return fallbackProvider.chat(userMessage, sessionId);
        }

        try {
            String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);

            String prompt = "You are the AI customer support assistant for RaniyaMart (an Indian e-commerce platform with INR pricing). " +
                            "Answer questions concisely regarding products, orders, shipping, and returns. " +
                            "User question: " + userMessage;

            String jsonPayload = "{\"contents\":[{\"parts\":[{\"text\":\"" + escapeJson(prompt) + "\"}]}]}";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
                String text = extractTextFromGeminiResponse(response.toString());
                if (text != null && !text.trim().isEmpty()) {
                    return text.trim();
                }
            }
        } catch (Exception ignored) {
            // Gracefully fall back to canned FAQ responses on timeout, connectivity, or invalid key
        }

        return fallbackProvider.chat(userMessage, sessionId);
    }

    private String escapeJson(String raw) {
        if (raw == null) return "";
        return raw.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\b", "\\b")
                  .replace("\f", "\\f")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }

    private String extractTextFromGeminiResponse(String json) {
        int textIdx = json.indexOf("\"text\": \"");
        if (textIdx != -1) {
            int start = textIdx + 9;
            int end = json.indexOf("\"", start);
            if (end != -1) {
                return json.substring(start, end).replace("\\n", "\n").replace("\\\"", "\"");
            }
        }
        return null;
    }
}
