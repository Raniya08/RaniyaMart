<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${empty products and empty param.q and empty param.category}">
    <jsp:forward page="/products" />
</c:if>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>RaniyaMart - Modern E-Commerce Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="brand-logo">
            🛍️ RaniyaMart
        </a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/products" class="active">Browse</a></li>
            <c:choose>
                <c:when test="${not empty sessionScope.currentUser}">
                    <c:if test="${sessionScope.currentUser.role eq 'BUYER'}">
                        <li><a href="${pageContext.request.contextPath}/cart">🛒 Cart</a></li>
                        <li><a href="${pageContext.request.contextPath}/orders">📦 Orders</a></li>
                    </c:if>
                    <c:if test="${sessionScope.currentUser.role eq 'SELLER'}">
                        <li><a href="${pageContext.request.contextPath}/seller/dashboard">🏬 Seller Dashboard</a></li>
                    </c:if>
                    <c:if test="${sessionScope.currentUser.role eq 'ADMIN'}">
                        <li><a href="${pageContext.request.contextPath}/admin/dashboard">⚙️ Admin Panel</a></li>
                    </c:if>
                    <li>
                        <span style="color: var(--text-secondary); font-size: 0.9rem;">
                            Hi, <strong style="color: var(--text-primary);"><c:out value="${sessionScope.currentUser.fullName}"/></strong>
                            <span class="badge badge-info"><c:out value="${sessionScope.currentUser.role}"/></span>
                        </span>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/logout" class="btn btn-secondary">Logout</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/login" class="btn btn-secondary">Login</a></li>
                    <li><a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Sign Up</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </nav>

    <div class="container">
        <c:if test="${not empty sessionScope.flashSuccess}">
            <div class="alert alert-success">
                <c:out value="${sessionScope.flashSuccess}"/>
                <c:remove var="flashSuccess" scope="session"/>
            </div>
        </c:if>

        <section class="hero">
            <h1>Discover Premium Products on RaniyaMart</h1>
            <p>Your one-stop destination for high-quality electronics, fashion, and lifestyle items.</p>

            <form action="${pageContext.request.contextPath}/products" method="GET" class="search-bar-container">
                <select name="category" class="select-input" style="max-width: 170px;">
                    <option value="all">All Categories</option>
                    <option value="Electronics" ${selectedCategory eq 'Electronics' ? 'selected' : ''}>Electronics</option>
                    <option value="Fashion" ${selectedCategory eq 'Fashion' ? 'selected' : ''}>Fashion</option>
                    <option value="Furniture" ${selectedCategory eq 'Furniture' ? 'selected' : ''}>Furniture</option>
                    <option value="Mobile Phones" ${selectedCategory eq 'Mobile Phones' ? 'selected' : ''}>Mobile Phones</option>
                    <option value="Home & Appliances" ${selectedCategory eq 'Home & Appliances' ? 'selected' : ''}>Home & Appliances</option>
                    <option value="Books & Media" ${selectedCategory eq 'Books & Media' ? 'selected' : ''}>Books & Media</option>
                    <option value="Other" ${selectedCategory eq 'Other' ? 'selected' : ''}>Other</option>
                </select>
                <select name="sort" class="select-input" style="max-width: 170px;">
                    <option value="newest" ${selectedSort eq 'newest' ? 'selected' : ''}>Newest Arrivals</option>
                    <option value="price_asc" ${selectedSort eq 'price_asc' ? 'selected' : ''}>Price: Low to High</option>
                    <option value="price_desc" ${selectedSort eq 'price_desc' ? 'selected' : ''}>Price: High to Low</option>
                </select>
                <input type="text" name="q" class="search-input" placeholder="Search products by title..." value="${fn:escapeXml(searchKeyword)}"/>
                <button type="submit" class="btn btn-primary">Search</button>
                <c:if test="${not empty searchKeyword || (not empty selectedCategory && selectedCategory ne 'all')}">
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-secondary" style="padding: 0.75rem 1rem;">Reset</a>
                </c:if>
            </form>
        </section>

        <h2>Featured Listings (${fn:length(products)})</h2>

        <div class="product-grid">
            <c:forEach var="p" items="${products}">
                <div class="product-card">
                    <img src="${fn:escapeXml(p.imageUrl)}" alt="${fn:escapeXml(p.name)}" class="product-image" onerror="this.src='https://images.unsplash.com/photo-1560343090-f0409e92791a?auto=format&fit=crop&w=600&q=80';"/>
                    <div class="product-info">
                        <span class="product-category"><c:out value="${p.category}"/></span>
                        <h3 class="product-title">
                            <a href="${pageContext.request.contextPath}/product?id=${p.id}" style="color: var(--text-primary); text-decoration: none;">
                                <c:out value="${p.name}"/>
                            </a>
                        </h3>
                        <p style="color: var(--text-secondary); font-size: 0.9rem; flex: 1;">
                            <c:out value="${fn:substring(p.description, 0, 90)}"/>...
                        </p>
                        <div class="product-price">
                            <span>₹<c:out value="${p.price}"/></span>
                            <c:if test="${empty sessionScope.currentUser || sessionScope.currentUser.role eq 'BUYER'}">
                                <form action="${pageContext.request.contextPath}/cart/add" method="POST" style="margin: 0;">
                                    <input type="hidden" name="productId" value="${p.id}"/>
                                    <input type="hidden" name="quantity" value="1"/>
                                    <button type="submit" class="btn btn-primary" style="padding: 0.4rem 0.8rem; font-size: 0.85rem;">+ Add to Cart</button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty products}">
                <div style="grid-column: 1 / -1; text-align: center; padding: 3rem; color: var(--text-secondary);">
                    <h3>No products found matching your search.</h3>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-secondary" style="margin-top: 1rem;">Clear Filters</a>
                </div>
            </c:if>
        </div>
    </div>

    <footer>
        <p>&copy; 2026 RaniyaMart Capstone. Built with Java Servlets, JDBC & Tomcat 9.0.x</p>
    </footer>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
