package com.raniya.raniyamart.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.raniya.raniyamart.model.User;
import com.raniya.raniyamart.service.AuthService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = authService.login(email, password);

        if (user == null) {
            request.setAttribute(
                    "error",
                    "Invalid email or password.");

            request.getRequestDispatcher(
                    "/login.jsp")
                    .forward(request, response);

            return;
        }

        HttpSession oldSession = request.getSession(false);

        if (oldSession != null) {
            oldSession.invalidate();
        }

        HttpSession session = request.getSession(true);

        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());
        session.setAttribute("userEmail", user.getEmail());
        session.setAttribute("userRole", user.getRole());

        session.setMaxInactiveInterval(30 * 60);

        response.sendRedirect(
                request.getContextPath()
                + "/index.jsp");
    }
}