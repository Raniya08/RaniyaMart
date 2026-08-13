<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.raniya.raniyamart.model.Product" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Products - RaniyaMart</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background: #f5f5f5;
        }

        header {
            background: #222;
            color: white;
            padding: 20px;
            text-align: center;
        }

        .container {
            width: 90%;
            margin: 30px auto;
        }

        .search-box {
            background: white;
            padding: 20px;
            margin-bottom: 25px;
            border-radius: 8px;
        }

        input, select, button {
            padding: 10px;
            margin: 5px;
        }

        button {
            cursor: pointer;
        }

        .products {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
            gap: 20px;
        }

        .product {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .product img {
            width: 100%;
            height: 180px;
            object-fit: cover;
            border-radius: 6px;
        }

        .price {
            font-size: 20px;
            font-weight: bold;
        }

        .stock {
            color: green;
        }

        .empty {
            background: white;
            padding: 30px;
            text-align: center;
        }
    </style>
</head>

<body>

<header>
    <h1>RaniyaMart</h1>
    <p>Products</p>
</header>

<div class="container">

    <div class="search-box">

        <form method="get"
              action="${pageContext.request.contextPath}/products">

            <input type="text"
                   name="keyword"
                   placeholder="Search products"
                   value="<%= request.getParameter("keyword") != null
                           ? request.getParameter("keyword") : "" %>">

            <select name="category">
                <option value="">All Categories</option>
                <option value="Grocery">Grocery</option>
                <option value="Clothing">Clothing</option>
                <option value="Electronics">Electronics</option>
                <option value="Beauty">Beauty</option>
                <option value="Home">Home</option>
            </select>

            <button type="submit">Search</button>

        </form>

    </div>

    <%
        List<Product> products =
                (List<Product>) request.getAttribute("products");
    %>

    <div class="products">

        <%
            if (products != null && !products.isEmpty()) {

                for (Product product : products) {
        %>

        <div class="product">

            <%
                if (product.getImageUrl() != null
                        && !product.getImageUrl().isEmpty()) {
            %>

                <img src="<%= product.getImageUrl() %>"
                     alt="<%= product.getName() %>">

            <%
                }
            %>

            <h2><%= product.getName() %></h2>

            <p>
                <%= product.getDescription() != null
                        ? product.getDescription()
                        : "" %>
            </p>

            <p class="price">
                ₹<%= product.getPrice() %>
            </p>

            <p class="stock">
                Stock: <%= product.getStockQty() %>
            </p>

            <p>
                Category:
                <%= product.getCategory() != null
                        ? product.getCategory()
                        : "General" %>
            </p>

        </div>

        <%
                }

            } else {
        %>

        <div class="empty">
            <h2>No products found</h2>
            <p>There are currently no products available.</p>
        </div>

        <%
            }
        %>

    </div>

    <br>

    <a href="${pageContext.request.contextPath}/index.jsp">
        Home
    </a>

</div>

</body>
</html>