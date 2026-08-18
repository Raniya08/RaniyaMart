package com.raniya.raniyamart.filter;

import com.raniya.raniyamart.dto.UserResponseDTO;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/cart/*", "/checkout/*", "/orders/*", "/seller/*", "/admin/*"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        UserResponseDTO currentUser = (session != null) ? (UserResponseDTO) session.getAttribute("currentUser") : null;

        if (currentUser == null) {
            String requestURI = httpRequest.getRequestURI();
            if (requestURI.startsWith(httpRequest.getContextPath() + "/api/")) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write("{\"success\":false,\"error\":\"Unauthorized access. Please login.\"}");
                return;
            }
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?error=Please+login+to+continue");
            return;
        }

        String uri = httpRequest.getRequestURI();
        String role = currentUser.getRole();

        // Role-based Access Control Checks
        if (uri.contains("/seller/") && !"SELLER".equalsIgnoreCase(role) && !"ADMIN".equalsIgnoreCase(role)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: Seller privilege required.");
            return;
        }

        if (uri.contains("/admin/") && !"ADMIN".equalsIgnoreCase(role)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: Admin privilege required.");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
