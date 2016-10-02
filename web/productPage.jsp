<%@ page import="java.util.Collection" %>
<%@ page import="shopcore.dto.ProductInfo" %>
<%@ page import="ui.UIProtocol" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  user: o_0
  Date: 2016-09-26
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<style>
    .shoppingCart {
        float: left;
        margin: 10px;
        padding: 10px;
        max-width: 400px;
        height: 300px;
        border: 1px solid black;
    }
</style>


<body>

<form action="LoginServlet">
    <input name="registry" type="submit" value="Login"/>
</form>

<table style="width:75%">
    <tr>
           
        <th align="left">ItemTitle</th>
           
        <th align="left">Description</th>
           
        <th align="left">Price</th>

        <th align="left">In Store</th>
           
        <th></th>
         
    </tr>

    <c:forEach items="${products}" var="product">
        <tr>
            <td><c:out value="${product.productTitle}"/></td>
            <td><c:out value="${product.description}"/></td>
            <td><c:out value="${product.price}"/></td>
            <td><c:out value="${product.quantity}"/></td>
            <td>
                <form action="ClientServlet">
                    <input name="productToAdd" type="hidden" value="${product.productId}">
                    <input name="addToCart" type="submit" value="Add To Cart"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>


<table style="width:50%">
    <tr>
           
        <th align="left">CartItem</th>
           
        <th align="left">cartPrice</th>

        <th align="left"></th>

           
        <th></th>
         
    </tr>

    <c:forEach items="${shoppingcart}" var="cart">
        <tr>
            <td><c:out value="${cart.productTitle}"/></td>
            <td><c:out value="${cart.price}"/></td>
            <td>
                <form action="ClientServlet">
                    <input name="productToRemove" type="hidden" value="${cart.productId}">
                    <input name="removeFromCart" type="submit" value="Remove"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="ClientServlet">
    <input name="goToRegestry" type="hidden" value="buy">
    <input name="registry" type="submit" value="Goto Registry"/>
</form>

<table style="width:50%">
    <tr>
           
        <th align="left">Orders</th>

        <th align="left"></th>

           
        <th></th>
         
    </tr>

    <c:forEach items="${orders}" var="order">
        <tr>
            <td><c:out value="${order.orderID}"/></td>
            <td>
                <form action="ClientServlet">
                    <input name="orderID" type="hidden" value="${order.orderID}">
                    <input name="showOrder" type="submit" value="Show"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
