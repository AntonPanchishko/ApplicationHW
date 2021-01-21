<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add car</title>
</head>
<body>
<h1>Provide new car</h1>
<h5 style="color: midnightblue">${message}</h5>
<form method="post" action="${pageContext.request.contextPath}/cars/add">
    Enter manufacturer_id<input type="number" min="1" name="manufacturer_id" required><br>
    Enter model<input type="text" name="model" required><br>
    <button type="submit">Add car</button>
</form>
</body>
</html>
