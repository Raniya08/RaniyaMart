<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order Confirmed - RaniyaMart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="brand-logo">🛍️ RaniyaMart</a>
    </nav>

    <div class="container" style="max-width: 650px; text-align: center; margin-top: 3rem;">
        <div class="table-container" style="padding: 3rem 2rem;">
            <div style="font-size: 4rem; margin-bottom: 1rem;">🎉</div>
            <h1 style="color: var(--success-color); margin-bottom: 0.5rem;">Order Placed Successfully!</h1>
            <p style="color: var(--text-secondary); margin-bottom: 2rem;">Thank you for shopping at RaniyaMart. Your order has been placed and confirmed.</p>

            <div style="background: var(--bg-surface); padding: 1.5rem; border-radius: 12px; text-align: left; margin-bottom: 2rem;">
                <p><strong>Order Reference ID:</strong> #<c:out value="${order.id}"/></p>
                <p><strong>Order Status:</strong> <span class="badge badge-success"><c:out value="${order.status}"/></span></p>
                <p><strong>Total Amount Paid:</strong> ₹<c:out value="${order.totalAmount}"/></p>
                <p><strong>Order Date:</strong> <c:out value="${order.createdAt}"/></p>
            </div>

            <div style="display: flex; gap: 1rem; justify-content: center;">
                <a href="${pageContext.request.contextPath}/orders" class="btn btn-primary">View My Orders</a>
                <a href="${pageContext.request.contextPath}/products" class="btn btn-secondary">Continue Shopping</a>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
