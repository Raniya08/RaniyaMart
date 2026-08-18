<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Orders - RaniyaMart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="brand-logo">🛍️ RaniyaMart</a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/products">Browse Catalog</a></li>
            <li><a href="${pageContext.request.contextPath}/cart">Cart</a></li>
            <li><a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">Logout</a></li>
        </ul>
    </nav>

    <div class="container">
        <h2>My Order History</h2>

        <c:choose>
            <c:when test="${not empty orders}">
                <div style="display: flex; flex-direction: column; gap: 1.5rem; margin-top: 1.5rem;">
                    <c:forEach var="ord" items="${orders}">
                        <div class="table-container" style="padding: 1.5rem;">
                            <div style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid var(--border-color); padding-bottom: 1rem; margin-bottom: 1rem;">
                                <div>
                                    <span style="font-weight: 700; font-size: 1.1rem;">Order #${ord.id}</span>
                                    <span style="color: var(--text-secondary); margin-left: 1rem; font-size: 0.9rem;"><c:out value="${ord.createdAt}"/></span>
                                </div>
                                <div>
                                    <span class="badge badge-success"><c:out value="${ord.status}"/></span>
                                    <span style="font-weight: 800; color: var(--success-color); margin-left: 1rem; font-size: 1.1rem;">₹<c:out value="${ord.totalAmount}"/></span>
                                </div>
                            </div>

                            <div style="display: flex; flex-direction: column; gap: 0.75rem;">
                                <c:forEach var="item" items="${ord.items}">
                                    <div style="display: flex; align-items: center; gap: 1rem;">
                                        <img src="${fn:escapeXml(item.product.imageUrl)}" alt="${fn:escapeXml(item.product.name)}" style="width: 45px; height: 45px; border-radius: 6px; object-fit: cover;"/>
                                        <div style="flex: 1;">
                                            <strong><c:out value="${item.product.name}"/></strong>
                                            <div style="font-size: 0.85rem; color: var(--text-secondary);">Qty: <c:out value="${item.quantity}"/> x ₹<c:out value="${item.pricePerUnit}"/></div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div style="text-align: center; padding: 4rem 1rem; color: var(--text-secondary);">
                    <h3>No past orders found.</h3>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary" style="margin-top: 1rem;">Start Shopping</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
