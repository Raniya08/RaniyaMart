package com.raniya.raniyamart.controller;

import com.raniya.raniyamart.dto.UserLoginDTO;
import com.raniya.raniyamart.dto.UserResponseDTO;
import com.raniya.raniyamart.exception.AppException;
import com.raniya.raniyamart.service.UserService;
import com.raniya.raniyamart.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserLoginDTO dto = new UserLoginDTO(email, password);

        try {
            UserResponseDTO authenticatedUser = userService.authenticate(dto);

            // Session Security: Invalidate old session and create fresh session (Session ID Regeneration)
            HttpSession oldSession = req.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession newSession = req.getSession(true);
            newSession.setAttribute("currentUser", authenticatedUser);
            newSession.setMaxInactiveInterval(30 * 60); // 30 minutes explicit timeout

            if ("SELLER".equalsIgnoreCase(authenticatedUser.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/seller/dashboard");
            } else if ("ADMIN".equalsIgnoreCase(authenticatedUser.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/products");
            }

        } catch (AppException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("email", email);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
