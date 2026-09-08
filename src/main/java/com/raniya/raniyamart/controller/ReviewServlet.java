package com.raniya.raniyamart.controller;

import com.raniya.raniyamart.dto.UserResponseDTO;
import com.raniya.raniyamart.service.ReviewService;
import com.raniya.raniyamart.service.impl.ReviewServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/product/review")
public class ReviewServlet extends HttpServlet {

    private ReviewService reviewService;

    @Override
    public void init() throws ServletException {
        this.reviewService = new ReviewServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        UserResponseDTO user = (session != null) ? (UserResponseDTO) session.getAttribute("currentUser") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String productIdStr = req.getParameter("productId");
        String ratingStr = req.getParameter("rating");
        String comment = req.getParameter("comment");

        try {
            Long productId = Long.parseLong(productIdStr);
            int rating = Integer.parseInt(ratingStr);

            reviewService.addReview(user.getId(), productId, rating, comment);
            session.setAttribute("flashSuccess", "Thank you! Your review has been submitted.");
            resp.sendRedirect(req.getContextPath() + "/product?id=" + productId);
        } catch (Exception e) {
            session.setAttribute("flashError", e.getMessage());
            if (productIdStr != null) {
                resp.sendRedirect(req.getContextPath() + "/product?id=" + productIdStr);
            } else {
                resp.sendRedirect(req.getContextPath() + "/products");
            }
        }
    }
}
