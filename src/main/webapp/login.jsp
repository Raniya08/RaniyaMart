<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - RaniyaMart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="brand-logo">🛍️ RaniyaMart</a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Sign Up</a></li>
        </ul>
    </nav>

    <div class="container">
        <div class="auth-card">
            <h2>Welcome Back</h2>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger"><c:out value="${errorMessage}"/></div>
            </c:if>

            <c:if test="${not empty param.error}">
                <div class="alert alert-danger"><c:out value="${param.error}"/></div>
            </c:if>

            <c:if test="${not empty param.success}">
                <div class="alert alert-success"><c:out value="${param.success}"/></div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="POST">
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" class="search-input" required value="${fn:escapeXml(email)}"/>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" class="search-input" required/>
                </div>
                <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 1rem;">Log In</button>
            </form>

            <p style="text-align: center; margin-top: 1.5rem; color: var(--text-secondary); font-size: 0.9rem;">
                Don't have an account? <a href="${pageContext.request.contextPath}/register" style="color: var(--accent-color);">Sign Up</a>
            </p>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
