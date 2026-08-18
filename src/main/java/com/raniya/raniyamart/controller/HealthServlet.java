package com.raniya.raniyamart.controller;

import com.raniya.raniyamart.util.DBUtil;
import com.raniya.raniyamart.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/v1/health")
public class HealthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> healthStatus = new HashMap<>();
        healthStatus.put("status", "UP");

        try (Connection conn = DBUtil.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                healthStatus.put("db", "UP");
            } else {
                healthStatus.put("db", "DOWN");
            }
        } catch (Exception e) {
            healthStatus.put("db", "DOWN");
        }

        JSONUtil.sendJsonResponse(resp, HttpServletResponse.SC_OK, healthStatus);
    }
}
