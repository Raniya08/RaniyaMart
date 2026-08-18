<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Product - RaniyaMart Seller</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/seller/dashboard" class="brand-logo">🛍️ RaniyaMart Seller</a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/seller/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">Logout</a></li>
        </ul>
    </nav>

    <div class="container" style="max-width: 650px;">
        <div class="auth-card" style="max-width: 100%;">
            <h2>Edit Product Listing</h2>

            <form action="${pageContext.request.contextPath}/seller/product/edit" method="POST">
                <input type="hidden" name="productId" value="${product.id}"/>

                <div class="form-group">
                    <label>Product Name</label>
                    <input type="text" name="name" class="search-input" required value="${fn:escapeXml(product.name)}"/>
                </div>

                <div class="form-group">
                    <label>Category</label>
                    <select name="category" class="select-input">
                        <option value="Electronics" ${product.category eq 'Electronics' ? 'selected' : ''}>Electronics</option>
                        <option value="Fashion" ${product.category eq 'Fashion' ? 'selected' : ''}>Fashion</option>
                        <option value="Furniture" ${product.category eq 'Furniture' ? 'selected' : ''}>Furniture</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Price (₹)</label>
                    <input type="number" step="0.01" name="price" class="search-input" required value="${product.price}"/>
                </div>

                <div class="form-group">
                    <label>Stock Quantity</label>
                    <input type="number" name="stockQty" class="search-input" required value="${product.stockQty}"/>
                </div>

                <div class="form-group">
                    <label>Image URL</label>
                    <input type="url" name="imageUrl" class="search-input" required value="${fn:escapeXml(product.imageUrl)}"/>
                </div>

                <div class="form-group">
                    <label>Description</label>
                    <textarea name="description" class="search-input" rows="4" required><c:out value="${product.description}"/></textarea>
                </div>

                <div style="display: flex; gap: 1rem; margin-top: 1.5rem;">
                    <button type="submit" class="btn btn-primary" style="flex: 1;">Save Changes</button>
                    <a href="${pageContext.request.contextPath}/seller/dashboard" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
