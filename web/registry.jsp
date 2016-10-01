<%--
  Created by IntelliJ IDEA.
  User: o_0
  Date: 2016-09-29
  Time: 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>registry</title>

</head>
<body>
<table style="width:50%">
    <tr>   
        <th align="left">CartItem</th>
        <th align="left">cartPrice</th>
        <th align="left"></th>
        <th></th>
    </tr>

    <c:forEach items="${shoppingcart}" var="cart">
        <tr>
            <td><c:out value="${cart.productTitle}" /></td>
            <td><c:out value="${cart.price}" /></td>
            <td>
                <form action="ClientServlet">
                    <input name="productToRemove" type="hidden" value="${cart.productId}">
                    <input name="removeFromCart" type="submit" value="Remove"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    <tr><td><c:out value="${totalPrice}" /></td></tr>

</table>
<form action="ClientServlet">
    <input name="lastPage" type="hidden" value="registry.jsp">
<input name="createBuyOrder" type="hidden" value="buy">
<input name="buy" type="submit" value="Buy"/>
</form>
</body>
</html>
