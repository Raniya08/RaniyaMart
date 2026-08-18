<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Seller Dashboard - RaniyaMart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="brand-logo">🛍️ RaniyaMart Seller</a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/products">Public Catalog</a></li>
            <li><a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">Logout</a></li>
        </ul>
    </nav>

    <div class="container">
        <h2>Seller Dashboard</h2>

        <c:if test="${not empty sessionScope.flashSuccess}">
            <div class="alert alert-success"><c:out value="${sessionScope.flashSuccess}"/><c:remove var="flashSuccess" scope="session"/></div>
        </c:if>
        <c:if test="${not empty sessionScope.flashError}">
            <div class="alert alert-danger"><c:out value="${sessionScope.flashError}"/><c:remove var="flashError" scope="session"/></div>
        </c:if>

        <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 2rem; margin-top: 1.5rem;">
            <div>
                <h3>My Active Product Listings (${fn:length(products)})</h3>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Category</th>
                                <th>Price</th>
                                <th>Stock Qty</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${products}">
                                <tr>
                                    <td><strong><c:out value="${p.name}"/></strong></td>
                                    <td><c:out value="${p.category}"/></td>
                                    <td>₹<c:out value="${p.price}"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${p.stockQty > 5}">
                                                <span class="badge badge-success"><c:out value="${p.stockQty}"/> in stock</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-warning">Low stock (<c:out value="${p.stockQty}"/>)</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td style="display: flex; gap: 0.5rem;">
                                        <a href="${pageContext.request.contextPath}/seller/product/edit?id=${p.id}" class="btn btn-secondary" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;">Edit</a>
                                        <form action="${pageContext.request.contextPath}/seller/product/delete" method="POST" style="margin: 0;">
                                            <input type="hidden" name="productId" value="${p.id}"/>
                                            <button type="submit" class="btn btn-danger" style="padding: 0.3rem 0.6rem; font-size: 0.8rem;">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="table-container" style="padding: 1.5rem; height: fit-content;">
                <h3>Add New Product Listing</h3>
                <form action="${pageContext.request.contextPath}/seller/product/add" method="POST">
                    <div class="form-group">
                        <label>Product Name</label>
                        <input type="text" name="name" class="search-input" required/>
                    </div>
                    <div class="form-group">
                        <label>Category</label>
                        <select name="category" class="select-input">
                            <option value="Electronics">Electronics</option>
                            <option value="Fashion">Fashion</option>
                            <option value="Furniture">Furniture</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Price (₹)</label>
                        <input type="number" step="0.01" name="price" class="search-input" required/>
                    </div>
                    <div class="form-group">
                        <label>Stock Quantity</label>
                        <input type="number" name="stockQty" class="search-input" value="10" required/>
                    </div>
                    <div class="form-group">
                        <label>Image URL</label>
                        <input type="url" name="imageUrl" class="search-input" placeholder="https://..." required/>
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <textarea name="description" class="search-input" rows="3" required></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary" style="width: 100%;">Create Listing</button>
                </form>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
