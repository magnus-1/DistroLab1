<%@ page import="java.util.Collection" %>
<%@ page import="shopcore.dto.ProductInfo" %>
<%@ page import="ui.UIProtocol" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: o_0
  Date: 2016-09-26
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>

<body>
<h2>Employee</h2>
<form action="EmployeeServlet" method="get">
    <input name="redirect" type="hidden" value="goToIndex">
    <input type="submit" value="Back">
</form>

<table style="width:75%">
    <tr>
           
        <th align="left">Order ID</th>
           
        <th align="left">User ID</th>
           
        <th align="left">Packed</th>
           
        <th></th>
         
    </tr>

    <c:forEach items="${orders}" var="order">
        <tr>
            <form action="EmployeeServlet">
                <td><c:out value="${order.orderID}" /></td>
                <td><c:out value="${order.userID}" /></td>
                <td><c:out value="${order.packed}" /></td>
                <td>
                    <input name="userID" type="hidden" value="${order.userID}">
                    <input name="orderID" type="hidden" value="${order.orderID}">
                    <input name="currentPage" type="hidden" value="employeePage">
                    <input name="orderToShow" type="hidden" value="${order.orderID}">
                    <input name="showOrder" type="submit" value="Show"/>
                    <input name="orderToPack" type="hidden" value="${order.orderID}">
                    <input name="packOrder" type="submit" value="Pack"/>
                </td>
            </form>
        </tr>
    </c:forEach>
</table>

</body>
</html>

