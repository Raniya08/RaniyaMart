package com.raniya.raniyamart.controller;

import com.raniya.raniyamart.dto.UserResponseDTO;
import com.raniya.raniyamart.model.Order;
import com.raniya.raniyamart.service.OrderService;
import com.raniya.raniyamart.service.UserService;
import com.raniya.raniyamart.service.impl.OrderServiceImpl;
import com.raniya.raniyamart.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminServlet extends HttpServlet {

    private UserService userService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserServiceImpl();
        this.orderService = new OrderServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserResponseDTO> users = userService.getAllUsers();
        List<Order> orders = orderService.getAllOrders();

        req.setAttribute("users", users);
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
    }
}
