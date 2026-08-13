<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>RaniyaMart</title>

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
            max-width: 900px;
            margin: 50px auto;
            background: white;
            padding: 40px;
            text-align: center;
            border-radius: 10px;
        }

        a {
            display: inline-block;
            margin: 10px;
            padding: 12px 25px;
            text-decoration: none;
            background: #222;
            color: white;
            border-radius: 5px;
        }
    </style>
</head>

<body>

<header>
    <h1>RaniyaMart</h1>
    <p>Your Online Shopping Platform</p>
</header>

<div class="container">

    <h2>Welcome to RaniyaMart</h2>

    <%
        Object userName = session.getAttribute("userName");

        if (userName == null) {
    %>

        <a href="${pageContext.request.contextPath}/login.jsp">
            Login
        </a>

        <a href="${pageContext.request.contextPath}/register.jsp">
            Register
        </a>

    <%
        } else {
    %>

        <h3>Welcome, <%= userName %>!</h3>

        <a href="${pageContext.request.contextPath}/products.jsp">
            Products
        </a>

        <a href="${pageContext.request.contextPath}/cart.jsp">
            Cart
        </a>

        <a href="${pageContext.request.contextPath}/logout">
            Logout
        </a>

    <%
        }
    %>

</div>

</body>
</html>