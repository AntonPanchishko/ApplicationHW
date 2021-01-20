<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver</title>
</head>
<body>
<h1>Provide new driver</h1>
<form method="post" action="${pageContext.request.contextPath}/drivers/add">
    Enter name<input type="text" name="name"><br>
    Enter licence number<input type="text" name="licence_number"><br>
    <button type="submit">Add driver</button>
</form>
</body>
</html>
