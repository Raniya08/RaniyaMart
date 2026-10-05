<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Account - RaniyaMart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="brand-logo">🛍️ RaniyaMart</a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/login" class="btn btn-secondary">Login</a></li>
        </ul>
    </nav>

    <div class="container">
        <div class="auth-card">
            <h2>Create an Account</h2>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger"><c:out value="${errorMessage}"/></div>
            </c:if>

            <form action="${pageContext.request.contextPath}/register" method="POST">
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" class="search-input" required value="${fn:escapeXml(fullName)}"/>
                </div>

                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" class="search-input" required value="${fn:escapeXml(email)}"/>
                </div>

                <div class="form-group">
                    <label for="password">Password (min 6 characters)</label>
                    <input type="password" id="password" name="password" class="search-input" minlength="6" required/>
                </div>

                <div class="form-group">
                    <label for="role">Account Role</label>
                    <select id="role" name="role" class="select-input">
                        <option value="BUYER" ${role eq 'BUYER' ? 'selected' : ''}>Buyer (Shop & Purchase)</option>
                        <option value="SELLER" ${role eq 'SELLER' ? 'selected' : ''}>Seller (List & Sell Products)</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 1rem;">Register Account</button>
            </form>

            <p style="text-align: center; margin-top: 1.5rem; color: var(--text-secondary); font-size: 0.9rem;">
                Already registered? <a href="${pageContext.request.contextPath}/login" style="color: var(--accent-color);">Log In</a>
            </p>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
