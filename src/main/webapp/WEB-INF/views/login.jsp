<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<h5>Login Page</h5>
<p style="color: darkred">${errorMsg}</p>
<form action="${pageContext.request.contextPath}/login" method="post">
    Enter login <input type="text" name="login" required><br>
    Enter password <input type="text" name="password" required><br>
    <button type="submit">Login</button>
</form>
</body>
</html>
