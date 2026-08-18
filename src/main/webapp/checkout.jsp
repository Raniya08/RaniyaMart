<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Checkout - RaniyaMart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="brand-logo">🛍️ RaniyaMart</a>
    </nav>

    <div class="container">
        <h2>Mock Checkout & Order Confirmation</h2>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger"><c:out value="${errorMessage}"/></div>
        </c:if>

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 2rem; margin-top: 2rem;">
            <div class="table-container" style="padding: 1.5rem;">
                <h3 style="margin-bottom: 1rem;">Shipping & Delivery Details</h3>
                <form id="checkout-form" action="${pageContext.request.contextPath}/checkout" method="POST">
                    <div class="form-group">
                        <label>Shipping Address</label>
                        <input type="text" class="search-input" value="123 Anna University Campus Road, Chennai" required/>
                    </div>
                    <div class="form-group">
                        <label>Contact Phone</label>
                        <input type="text" class="search-input" value="+91 98765 43210" required/>
                    </div>
                    <div class="form-group">
                        <label>Payment Method</label>
                        <select class="select-input" disabled>
                            <option selected>Mock Gateway (Demo Instant Confirmation)</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 1.5rem; padding: 0.8rem; font-size: 1.1rem;">Place Order Now (₹<c:out value="${cartTotal}"/>)</button>
                </form>
            </div>

            <div class="table-container" style="padding: 1.5rem;">
                <h3 style="margin-bottom: 1rem;">Order Summary</h3>
                <c:forEach var="item" items="${cartItems}">
                    <div style="display: flex; justify-content: space-between; margin-bottom: 0.75rem; padding-bottom: 0.75rem; border-bottom: 1px solid var(--border-color);">
                        <div>
                            <strong><c:out value="${item.product.name}"/></strong>
                            <div style="font-size: 0.85rem; color: var(--text-secondary);">Qty: <c:out value="${item.quantity}"/> x ₹<c:out value="${item.product.price}"/></div>
                        </div>
                        <div style="font-weight: 700; color: var(--success-color);">₹<c:out value="${item.product.price * item.quantity}"/></div>
                    </div>
                </c:forEach>
                <div style="display: flex; justify-content: space-between; font-size: 1.3rem; font-weight: 800; margin-top: 1.5rem;">
                    <span>Total Amount:</span>
                    <span style="color: var(--success-color);">₹<c:out value="${cartTotal}"/></span>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
