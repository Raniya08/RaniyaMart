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
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
