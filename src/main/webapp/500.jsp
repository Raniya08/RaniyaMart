<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>500 - Internal Server Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="brand-logo">🛍️ RaniyaMart</a>
    </nav>
    <div class="container" style="text-align: center; margin-top: 5rem;">
        <h1 style="font-size: 5rem; color: var(--danger-color);">500</h1>
        <h2>Internal Server Error</h2>
        <p style="color: var(--text-secondary); margin: 1rem 0 2rem;">An unexpected internal error occurred while processing your request. Please try again later.</p>
        <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Return to Catalog</a>
    </div>
</body>
</html>
