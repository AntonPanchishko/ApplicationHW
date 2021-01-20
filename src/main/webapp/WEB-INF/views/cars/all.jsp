<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All cars</title>
</head>
<body>
<h1>All cars page</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>model</th>
        <th>manufacturer</th>
        <th>drivers</th>
    </tr>
    <c:forEach var="car" items="${cars}">
        <tr>
            <td>
                <c:out value="${car.id}"/>
            </td>
            <td>
                <c:out value="${car.model}"/>
            </td>
            <td>
                <c:out value="${car.manufacturer.model}"/>
            </td>
            <td>
                <c:forEach var="driver" items="${car.drivers}">
                    <table border="1px">
                        <td><c:out value="${driver.name}"/></td>
                        <td><c:out value="${driver.licenceNumber}"/></td>
                    </table>
                </c:forEach>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/cars/delete?car_id=${car.id}">delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>