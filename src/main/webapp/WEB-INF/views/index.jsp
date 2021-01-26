<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
<h1>Hello on main page!</h1>
<p><a href="${pageContext.request.contextPath}/manufacturers/add">create Manufacturer</a></p>
<p><a href="${pageContext.request.contextPath}/drivers/add">create Driver</a></p>
<p><a href="${pageContext.request.contextPath}/cars/add">create Car</a></p>

<p><a href="${pageContext.request.contextPath}/manufacturers/all">all Manufacturers</a></p>
<p><a href="${pageContext.request.contextPath}/drivers/all">all Drivers</a></p>
<p><a href="${pageContext.request.contextPath}/cars/all">all Cars</a></p>

<p><a href="${pageContext.request.contextPath}/login">Login</a></p>
</body>
</html>
