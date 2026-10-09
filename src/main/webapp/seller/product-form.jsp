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
                        <option value="Beauty & Personal Care" ${product.category eq 'Beauty & Personal Care' ? 'selected' : ''}>Beauty & Personal Care</option>
                        <option value="Fashion" ${product.category eq 'Fashion' ? 'selected' : ''}>Fashion</option>
                        <option value="Furniture" ${product.category eq 'Furniture' ? 'selected' : ''}>Furniture</option>
                        <option value="Mobile Phones" ${product.category eq 'Mobile Phones' ? 'selected' : ''}>Mobile Phones</option>
                        <option value="Home & Appliances" ${product.category eq 'Home & Appliances' ? 'selected' : ''}>Home & Appliances</option>
                        <option value="Books & Media" ${product.category eq 'Books & Media' ? 'selected' : ''}>Books & Media</option>
                        <option value="Other" ${product.category eq 'Other' ? 'selected' : ''}>Other</option>
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
                    <label>Image URL (Optional)</label>
                    <input type="text" id="edit-img-input" name="imageUrl" class="search-input" value="${fn:escapeXml(product.imageUrl)}"/>
                    <div style="display: flex; gap: 0.4rem; margin-top: 0.5rem; flex-wrap: wrap;">
                        <small style="color: var(--text-secondary); width: 100%;">Sample Image Pickers:</small>
                        <button type="button" class="btn btn-secondary" style="padding: 0.2rem 0.5rem; font-size: 0.75rem;" onclick="document.getElementById('edit-img-input').value='https://images.unsplash.com/photo-1598440947619-2c35fc9aa908?auto=format&fit=crop&w=600&q=80'">🧴 Sunscreen / Skincare</button>
                        <button type="button" class="btn btn-secondary" style="padding: 0.2rem 0.5rem; font-size: 0.75rem;" onclick="document.getElementById('edit-img-input').value='https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?auto=format&fit=crop&w=600&q=80'">📱 Phone</button>
                        <button type="button" class="btn btn-secondary" style="padding: 0.2rem 0.5rem; font-size: 0.75rem;" onclick="document.getElementById('edit-img-input').value='https://images.unsplash.com/photo-1496181133206-80ce9b88a853?auto=format&fit=crop&w=600&q=80'">💻 Laptop</button>
                        <button type="button" class="btn btn-secondary" style="padding: 0.2rem 0.5rem; font-size: 0.75rem;" onclick="document.getElementById('edit-img-input').value='https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=600&q=80'">⌚ Watch</button>
                        <button type="button" class="btn btn-secondary" style="padding: 0.2rem 0.5rem; font-size: 0.75rem;" onclick="document.getElementById('edit-img-input').value='https://images.unsplash.com/photo-1580481072645-022f9a6d1270?auto=format&fit=crop&w=600&q=80'">🪑 Furniture</button>
                    </div>
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
