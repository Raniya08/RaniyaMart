<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${product.name}"/> - RaniyaMart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/products" class="brand-logo">🛍️ RaniyaMart</a>
        <ul class="nav-links">
            <li><a href="${pageContext.request.contextPath}/products">Browse Catalog</a></li>
            <c:if test="${not empty sessionScope.currentUser}">
                <li><a href="${pageContext.request.contextPath}/cart">Cart</a></li>
                <li><a href="${pageContext.request.contextPath}/orders">Orders</a></li>
            </c:if>
        </ul>
    </nav>

    <div class="container" style="margin-top: 2rem;">
        <c:if test="${not empty sessionScope.flashSuccess}">
            <div class="alert alert-success"><c:out value="${sessionScope.flashSuccess}"/><c:remove var="flashSuccess" scope="session"/></div>
        </c:if>
        <c:if test="${not empty sessionScope.flashError}">
            <div class="alert alert-danger"><c:out value="${sessionScope.flashError}"/><c:remove var="flashError" scope="session"/></div>
        </c:if>

        <a href="${pageContext.request.contextPath}/products" class="btn btn-secondary" style="margin-bottom: 1.5rem;">&larr; Back to Catalog</a>

        <div style="display: grid; grid-template-columns: 1fr 1.2fr; gap: 2.5rem; background: var(--bg-card); padding: 2rem; border-radius: var(--radius-lg); border: 1px solid var(--border-color);">
            <div>
                <img src="${fn:escapeXml(product.imageUrl)}" alt="${fn:escapeXml(product.name)}" style="width: 100%; height: 350px; object-fit: cover; border-radius: 12px;"/>
            </div>

            <div style="display: flex; flex-direction: column;">
                <span class="product-category"><c:out value="${product.category}"/></span>
                <h1 style="font-size: 2rem; margin-bottom: 0.5rem;"><c:out value="${product.name}"/></h1>
                
                <div style="display: flex; align-items: center; gap: 0.5rem; margin-bottom: 0.5rem;">
                    <span style="color: #f59e0b; font-size: 1.2rem;">★ <c:out value="${avgRating > 0 ? avgRating : 'New'}"/></span>
                    <span style="color: var(--text-secondary); font-size: 0.9rem;">(${fn:length(reviews)} customer reviews)</span>
                </div>

                <div style="font-size: 2rem; font-weight: 800; color: var(--success-color); margin: 1rem 0;">
                    ₹<c:out value="${product.price}"/>
                </div>

                <p style="color: var(--text-secondary); line-height: 1.7; flex: 1;">
                    <c:out value="${product.description}"/>
                </p>

                <div style="margin-top: 1.5rem; padding-top: 1.5rem; border-top: 1px solid var(--border-color); display: flex; align-items: center; justify-content: space-between;">
                    <div>
                        <c:choose>
                            <c:when test="${product.stockQty > 0}">
                                <span class="badge badge-success"><c:out value="${product.stockQty}"/> In Stock</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-warning">Out of Stock</span>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <c:if test="${empty sessionScope.currentUser || sessionScope.currentUser.role eq 'BUYER'}">
                        <form action="${pageContext.request.contextPath}/cart/add" method="POST" style="margin: 0; display: flex; gap: 0.5rem;">
                            <input type="hidden" name="productId" value="${product.id}"/>
                            <input type="number" name="quantity" value="1" min="1" max="${product.stockQty}" class="search-input" style="width: 70px;"/>
                            <button type="submit" class="btn btn-primary" ${product.stockQty == 0 ? 'disabled' : ''}>Add to Cart</button>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>

        <!-- Customer Reviews & Rating Section -->
        <div style="margin-top: 3rem; background: var(--bg-card); padding: 2rem; border-radius: var(--radius-lg); border: 1px solid var(--border-color);">
            <h2 style="margin-bottom: 1.5rem;">Customer Reviews & Ratings</h2>

            <c:if test="${not empty sessionScope.currentUser}">
                <div style="background: var(--bg-surface); padding: 1.5rem; border-radius: 12px; margin-bottom: 2rem;">
                    <h3>Write a Review</h3>
                    <form action="${pageContext.request.contextPath}/product/review" method="POST">
                        <input type="hidden" name="productId" value="${product.id}"/>
                        <div class="form-group" style="margin-top: 1rem;">
                            <label>Rating (1 to 5 Stars)</label>
                            <select name="rating" class="select-input" style="max-width: 150px;" required>
                                <option value="5">★★★★★ (5 Stars)</option>
                                <option value="4">★★★★☆ (4 Stars)</option>
                                <option value="3">★★★☆☆ (3 Stars)</option>
                                <option value="2">★★☆☆☆ (2 Stars)</option>
                                <option value="1">★☆☆☆☆ (1 Star)</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Review Details</label>
                            <textarea name="comment" class="search-input" rows="3" placeholder="Share your experience with this product..." required></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">Submit Review</button>
                    </form>
                </div>
            </c:if>

            <c:choose>
                <c:when test="${not empty reviews}">
                    <div style="display: flex; flex-direction: column; gap: 1rem;">
                        <c:forEach var="r" items="${reviews}">
                            <div style="padding: 1rem; border-bottom: 1px solid var(--border-color);">
                                <div style="display: flex; justify-content: space-between; align-items: center;">
                                    <span style="color: #f59e0b; font-weight: 700;">
                                        <c:forEach begin="1" end="${r.rating}">★</c:forEach>
                                    </span>
                                    <span style="color: var(--text-secondary); font-size: 0.85rem;"><c:out value="${r.createdAt}"/></span>
                                </div>
                                <p style="margin-top: 0.5rem; color: var(--text-primary);"><c:out value="${r.comment}"/></p>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <p style="color: var(--text-secondary);">No reviews yet. Be the first to write a review!</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
