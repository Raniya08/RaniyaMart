<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - RaniyaMart</title>
</head>

<body>

<h1>RaniyaMart Login</h1>

<%
    String error = (String) request.getAttribute("error");

    if (error != null) {
%>

    <p style="color:red;"><%= error %></p>

<%
    }

    if ("true".equals(request.getParameter("registered"))) {
%>

    <p style="color:green;">
        Registration successful. Please login.
    </p>

<%
    }
%>

<form method="post"
      action="${pageContext.request.contextPath}/login">

    <label>Email:</label><br>
    <input type="email" name="email" required>
    <br><br>

    <label>Password:</label><br>
    <input type="password" name="password" required>
    <br><br>

    <button type="submit">Login</button>

</form>

<br>

<a href="${pageContext.request.contextPath}/register.jsp">
    Create Account
</a>

<br><br>

<a href="${pageContext.request.contextPath}/index.jsp">
    Home
</a>

</body>
</html>