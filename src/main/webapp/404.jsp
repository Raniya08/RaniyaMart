<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>404 - Page Not Found</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="brand-logo">🛍️ RaniyaMart</a>
    </nav>
    <div class="container" style="text-align: center; margin-top: 5rem;">
        <h1 style="font-size: 5rem; color: var(--accent-color);">404</h1>
        <h2>Oops! Page Not Found</h2>
        <p style="color: var(--text-secondary); margin: 1rem 0 2rem;">The page or resource you requested could not be located.</p>
        <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Return to Catalog</a>
    </div>
</body>
</html>
