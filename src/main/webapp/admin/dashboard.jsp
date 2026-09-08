<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - RaniyaMart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="brand-logo">⚙️ RaniyaMart Admin</a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/products">Public Catalog</a></li>
            <li><a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">Logout</a></li>
        </ul>
    </nav>

    <div class="container">
        <h2>Admin Management Panel</h2>

        <c:if test="${not empty sessionScope.flashSuccess}">
            <div class="alert alert-success"><c:out value="${sessionScope.flashSuccess}"/><c:remove var="flashSuccess" scope="session"/></div>
        </c:if>
        <c:if test="${not empty sessionScope.flashError}">
            <div class="alert alert-danger"><c:out value="${sessionScope.flashError}"/><c:remove var="flashError" scope="session"/></div>
        </c:if>

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 2rem; margin-top: 1.5rem;">
            <div>
                <h3>Registered Users (${fn:length(users)})</h3>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Role</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${users}">
                                <tr>
                                    <td>#<c:out value="${u.id}"/></td>
                                    <td><strong><c:out value="${u.fullName}"/></strong></td>
                                    <td><c:out value="${u.email}"/></td>
                                    <td><span class="badge badge-info"><c:out value="${u.role}"/></span></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div>
                <h3>System Orders (${fn:length(orders)})</h3>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Buyer ID</th>
                                <th>Total</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="o" items="${orders}">
                                <tr>
                                    <td>#<c:out value="${o.id}"/></td>
                                    <td>Buyer #<c:out value="${o.buyerId}"/></td>
                                    <td>₹<c:out value="${o.totalAmount}"/></td>
                                    <td><span class="badge badge-success"><c:out value="${o.status}"/></span></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Section 3: Product Listing Moderation -->
        <div style="margin-top: 3rem;">
            <h3>Product Listings Moderation (${fn:length(products)})</h3>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Product Name</th>
                            <th>Category</th>
                            <th>Price</th>
                            <th>Stock</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${products}">
                            <tr>
                                <td>#<c:out value="${p.id}"/></td>
                                <td><strong><c:out value="${p.name}"/></strong></td>
                                <td><c:out value="${p.category}"/></td>
                                <td>₹<c:out value="${p.price}"/></td>
                                <td><c:out value="${p.stockQty}"/></td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/admin/product/delete" method="POST" style="margin: 0;">
                                        <input type="hidden" name="productId" value="${p.id}"/>
                                        <button type="submit" class="btn btn-danger" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;">Moderate / Remove</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
