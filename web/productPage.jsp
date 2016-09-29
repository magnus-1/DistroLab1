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


<table style="width:100%">
    <tr>
           
        <th align="left">ItemTitle</th>
           
        <th align="left">Description</th>
           
        <th align="left">Price</th>

        <th align="left">In Store</th>
           
        <th></th>
         
    </tr>



    <c:forEach items="${products}" var="product">
    <tr>
        <td><c:out value="${product.productTitle}" /></td>
        <td><c:out value="${product.description}" /></td>
        <td><c:out value="${product.price}" /></td>
        <td><c:out value="${product.quantity}" /></td>
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


    <%--<c:forEach items="${cookie}" var="mycookie" >--%>
        <%--<tr>--%>
            <%--<td><p>Key: <c:out value="${mycookie.key}" /></p></td>--%>
            <%--<td><p>Value: <c:out value="${mycookie.value}" /></p></td>--%>

            <%--<td><p>Value: <c:out value="${mycookie.value.name}" /></p></td>--%>
            <%--<td><p>Value: <c:out value="${mycookie.value.value}" /></p></td>--%>

        <%--</tr>--%>
    <%--</c:forEach>--%>

    <c:forEach items="${shoppingcart}" var="cart">
        <tr>
            <td><c:out value="${cart.productTitle}" /></td>
            <td><c:out value="${cart.price}" /></td>
            <td>
                <form action="ClientServlet">
                    <input name="productToRemove" type="hidden" value="${product.productId}">
                    <input name="removeFromCart" type="submit" value="Remove"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

    </body>
</html>
