<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - RaniyaMart</title>
</head>

<body>

<h1>RaniyaMart Registration</h1>

<%
    String error = (String) request.getAttribute("error");

    if (error != null) {
%>

    <p style="color:red;"><%= error %></p>

<%
    }
%>

<form method="post"
      action="${pageContext.request.contextPath}/register">

    <label>Name:</label><br>
    <input type="text"
           name="name"
           required>
    <br><br>

    <label>Email:</label><br>
    <input type="email"
           name="email"
           required>
    <br><br>

    <label>Password:</label><br>
    <input type="password"
           name="password"
           minlength="6"
           required>
    <br><br>

    <label>Role:</label><br>

    <select name="role" required>

        <option value="BUYER">
            Buyer
        </option>

        <option value="SELLER">
            Seller
        </option>

    </select>

    <br><br>

    <button type="submit">
        Register
    </button>

</form>

<br>

<a href="${pageContext.request.contextPath}/login.jsp">
    Already have an account? Login
</a>

</body>
</html>