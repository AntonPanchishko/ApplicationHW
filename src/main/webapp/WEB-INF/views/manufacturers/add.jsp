<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add manufacturer</title>
</head>
<body>
<h1>Provide new manufacturers</h1>
<form method="post" action="${pageContext.request.contextPath}/manufacturers/add">
    Enter name<input type="text" name="name" required><br>
    Enter country<input type="text" name="country" required><br>
    <button type="submit">Add manufacturer</button>
</form>
</body>
</html>
