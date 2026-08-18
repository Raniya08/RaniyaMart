package com.raniya.raniyamart.controller;

import com.raniya.raniyamart.dto.UserRegisterDTO;
import com.raniya.raniyamart.dto.UserResponseDTO;
import com.raniya.raniyamart.exception.AppException;
import com.raniya.raniyamart.service.UserService;
import com.raniya.raniyamart.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        UserRegisterDTO dto = new UserRegisterDTO(fullName, email, password, role);

        try {
            UserResponseDTO registeredUser = userService.register(dto);
            req.getSession().setAttribute("flashSuccess", "Registration successful! Please log in.");
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (AppException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("fullName", fullName);
            req.setAttribute("email", email);
            req.setAttribute("role", role);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
