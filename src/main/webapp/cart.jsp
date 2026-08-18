<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart - RaniyaMart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="brand-logo">🛍️ RaniyaMart</a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/products">Browse Catalog</a></li>
            <li><a href="${pageContext.request.contextPath}/orders">Orders</a></li>
            <li><a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">Logout</a></li>
        </ul>
    </nav>

    <div class="container">
        <h2>Your Shopping Cart</h2>

        <c:if test="${not empty sessionScope.flashSuccess}">
            <div class="alert alert-success"><c:out value="${sessionScope.flashSuccess}"/><c:remove var="flashSuccess" scope="session"/></div>
        </c:if>
        <c:if test="${not empty sessionScope.flashError}">
            <div class="alert alert-danger"><c:out value="${sessionScope.flashError}"/><c:remove var="flashError" scope="session"/></div>
        </c:if>

        <c:choose>
            <c:when test="${not empty cartItems}">
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Subtotal</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${cartItems}">
                                <tr>
                                    <td style="display: flex; align-items: center; gap: 1rem;">
                                        <img src="${fn:escapeXml(item.product.imageUrl)}" alt="${fn:escapeXml(item.product.name)}" style="width: 50px; height: 50px; border-radius: 8px; object-fit: cover;"/>
                                        <div>
                                            <strong><c:out value="${item.product.name}"/></strong>
                                            <div style="font-size: 0.8rem; color: var(--text-secondary);"><c:out value="${item.product.category}"/></div>
                                        </div>
                                    </td>
                                    <td>₹<c:out value="${item.product.price}"/></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/cart/update" method="POST" style="display: flex; gap: 0.5rem; align-items: center;">
                                            <input type="hidden" name="itemId" value="${item.id}"/>
                                            <input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.product.stockQty}" class="search-input" style="width: 70px; padding: 0.3rem 0.5rem;"/>
                                            <button type="submit" class="btn btn-secondary" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;">Update</button>
                                        </form>
                                    </td>
                                    <td style="font-weight: 700; color: var(--success-color);">₹<c:out value="${item.product.price * item.quantity}"/></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/cart/remove" method="POST">
                                            <input type="hidden" name="itemId" value="${item.id}"/>
                                            <button type="submit" class="btn btn-danger" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;">Remove</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 2rem; background: var(--bg-card); padding: 1.5rem; border-radius: var(--radius-lg); border: 1px solid var(--border-color);">
                    <div>
                        <span style="color: var(--text-secondary);">Total Items: </span>
                        <strong>${fn:length(cartItems)}</strong>
                    </div>
                    <div style="text-align: right;">
                        <div style="font-size: 1.5rem; font-weight: 800; color: var(--success-color); margin-bottom: 1rem;">
                            Grand Total: ₹<c:out value="${cartTotal}"/>
                        </div>
                        <a href="${pageContext.request.contextPath}/checkout" class="btn btn-primary" style="padding: 0.8rem 2rem; font-size: 1.1rem;">Proceed to Checkout &rarr;</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div style="text-align: center; padding: 4rem 1rem; color: var(--text-secondary);">
                    <h3>Your cart is empty.</h3>
                    <p style="margin-top: 0.5rem;">Explore our catalog and start shopping!</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary" style="margin-top: 1.5rem;">Browse Products</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
