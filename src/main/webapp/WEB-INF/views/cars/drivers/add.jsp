<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to car</title>
</head>
<body>
<h1>Provide driver to car</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
    car id: <input type="number" min="1" name="car_id" required><br>
    driver id: <input type="number" min="1" name="driver_id" required><br>
    <button type="submit">add</button>
</form>
</body>
</html>
