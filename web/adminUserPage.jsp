<%@ page import="java.util.Collection" %>
<%@ page import="ui.ProductInfo" %>
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
<h2>Admin/Users</h2>
<form action="AdminServlet" method="get">
    <input name="redirect" type="hidden" value="goToIndex">
    <input type="submit" value="Back">
</form>

<table style="width:75%">
    <tr>
           
        <th align="left">UserEmail</th>
           
        <th align="left">UserPassword</th>
           
        <th align="left">UserType</th>

        <th></th>
         
    </tr>

    <tr>
        <form action="AdminServlet">
            <td>
                <input name="userEmail" value="">
            </td>
            <td>
                <input name="userPassword" value="">
            </td>

            <td>
                <input name="userType" value="">
            </td>

            <td>
                <input name="currentPage" type="hidden" value="adminUsers">
                <input name="userToAdd" type="hidden" value="">
                <input name="addProduct" type="submit" value="Add Product"/>
            </td>
        </form>
    </tr>

    <c:forEach items="${users}" var="user">
        <tr>
            <form action="AdminServlet">
                <td>
                    <input name="userEmail" value="${user.email}">
                </td>
                <td>
                    <input name="userPassword" value="${user.password}">
                </td>

                <td>
                    <input name="userType" value="${user.userType}">
                </td>

                <td>
                    <input name="currentPage" type="hidden" value="adminUsers">
                    <input name="userToDelete" type="hidden" value="${user.userID}">
                    <input name="deleteUser" type="submit" value="Delete"/>
                    <input name="userToUpdate" type="hidden" value="${user.userID}">
                    <input name="updateUser" type="submit" value="Update"/>
                </td>
            </form>
        </tr>
    </c:forEach>
</table>

</body>
</html>