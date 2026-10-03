package com.raniya.raniyamart.controller;

import com.raniya.raniyamart.service.ai.ChatProvider;
import com.raniya.raniyamart.service.ai.ChatProviderFactory;
import com.raniya.raniyamart.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(urlPatterns = {"/api/chat", "/api/v1/chat"})
public class ChatServlet extends HttpServlet {

    private ChatProvider chatProvider;

    @Override
    public void init() throws ServletException {
        this.chatProvider = ChatProviderFactory.getProvider();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(true);
        String sessionId = session.getId();

        // Enforce Per-Session Rate Limit (10 messages per minute)
        Long now = System.currentTimeMillis();
        Long windowStart = (Long) session.getAttribute("chat_window_start");
        Integer messageCount = (Integer) session.getAttribute("chat_msg_count");

        if (windowStart == null || (now - windowStart) > 60000) {
            session.setAttribute("chat_window_start", now);
            session.setAttribute("chat_msg_count", 1);
        } else {
            if (messageCount != null && messageCount >= 10) {
                Map<String, Object> err = new HashMap<>();
                err.put("success", false);
                err.put("error", "Rate limit exceeded. Please wait a minute before sending another message.");
                JSONUtil.sendJsonResponse(resp, 429, err);
                return;
            }
            session.setAttribute("chat_msg_count", (messageCount != null ? messageCount : 0) + 1);
        }

        // Parse JSON or Parameter input
        String userMessage = extractMessage(req);

        if (userMessage == null || userMessage.trim().isEmpty()) {
            Map<String, Object> err = new HashMap<>();
            err.put("success", false);
            err.put("error", "Message content cannot be empty.");
            JSONUtil.sendJsonResponse(resp, HttpServletResponse.SC_BAD_REQUEST, err);
            return;
        }

        if (userMessage.length() > 500) {
            Map<String, Object> err = new HashMap<>();
            err.put("success", false);
            err.put("error", "Message length exceeds maximum allowed limit of 500 characters.");
            JSONUtil.sendJsonResponse(resp, HttpServletResponse.SC_BAD_REQUEST, err);
            return;
        }

        // Session Response Caching for Identical Queries
        @SuppressWarnings("unchecked")
        Map<String, String> cache = (Map<String, String>) session.getAttribute("chat_cache");
        if (cache == null) {
            cache = new ConcurrentHashMap<>();
            session.setAttribute("chat_cache", cache);
        }

        String cacheKey = userMessage.trim().toLowerCase();
        String reply = cache.get(cacheKey);

        if (reply == null) {
            reply = chatProvider.chat(userMessage, sessionId);
            cache.put(cacheKey, reply);
        }

        Map<String, Object> success = new HashMap<>();
        success.put("success", true);
        success.put("reply", reply);
        JSONUtil.sendJsonResponse(resp, HttpServletResponse.SC_OK, success);
    }

    private String extractMessage(HttpServletRequest req) throws IOException {
        String msg = req.getParameter("message");
        if (msg != null && !msg.trim().isEmpty()) {
            return msg;
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        String body = sb.toString();
        if (body.contains("\"message\":")) {
            int start = body.indexOf("\"message\":") + 10;
            int quoteStart = body.indexOf("\"", start);
            if (quoteStart != -1) {
                int quoteEnd = body.indexOf("\"", quoteStart + 1);
                if (quoteEnd != -1) {
                    return body.substring(quoteStart + 1, quoteEnd);
                }
            }
        }
        return null;
    }
}
