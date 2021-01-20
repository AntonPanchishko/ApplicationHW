<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add car</title>
</head>
<body>
<h1>Provide new car</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/add">
    Enter manufacturer_id<input type="text" name="manufacturer_id"><br>
    Enter model<input type="text" name="model"><br>
    <button type="submit">Add car</button>
</form>
</body>
</html>
