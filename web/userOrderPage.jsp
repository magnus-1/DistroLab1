<%--
  Created by IntelliJ IDEA.
  User: o_0
  Date: 2016-10-02
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Collection" %>
<%@ page import="shopcore.dto.ProductInfo" %>
<%@ page import="ui.UIProtocol" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>

<body>
<h2>Employee</h2>
<form action="ClientServlet" method="get">
    <input name="redirect" type="hidden" value="goToEmployeePage">
    <input type="submit" value="Back">
</form>

<table style="width:50%">
    <tr>
           
        <th align="left">ItemTitle</th>
           
        <th align="left">Description</th>
           
        <th align="left">Price</th>
           
        <th></th>
         
    </tr>

    <c:forEach items="${productsInOrder}" var="product">
        <tr>
            <td><c:out value="${product.productTitle}"/></td>
            <td><c:out value="${product.description}"/></td>
            <td><c:out value="${product.price}"/></td>
        </tr>
    </c:forEach>
    <td><c:out value="${totalPrice}"/></td>
</table>

</body>
</html>
