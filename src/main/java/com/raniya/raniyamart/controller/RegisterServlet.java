package com.raniya.raniyamart.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raniya.raniyamart.service.AuthService;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/register.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        boolean registered =
                authService.register(
                        name,
                        email,
                        password,
                        role);

        if (registered) {
            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp?registered=true");
        } else {
            request.setAttribute(
                    "error",
                    "Registration failed. Check your details or email.");
            request.getRequestDispatcher(
                    "/register.jsp")
                    .forward(request, response);
        }
    }
}