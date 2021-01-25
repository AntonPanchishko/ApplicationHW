<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>All manufacturers page</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Model</th>
        <th>Country</th>
        <c:forEach var="manufacturer" items="${manufacturers}">
    <tr>
        <td>
            <c:out value="${manufacturer.id}"/>
        </td>
        <td>
            <c:out value="${manufacturer.model}"/>
        </td>
        <td>
            <c:out value="${manufacturer.country}"/>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/manufacturers/delete?manufacturer_id=${manufacturer.id}">delete</a>
        </td>
    </tr>
    </c:forEach>
    </tr>
</table>
</body>
</html>
