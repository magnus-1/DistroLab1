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
<h2>Admin/Products</h2>
<form action="AdminServlet" method="get">
    <input name="redirect" type="hidden" value="goToIndex">
    <input type="submit" value="Back">
</form>

<table style="width:75%">
    <tr>
           
        <th align="left">ItemTitle</th>
           
        <th align="left">Description</th>
           
        <th align="left">Category</th>
           
        <th align="left">Price</th>

        <th align="left">Quantity</th>
           
        <th></th>
         
    </tr>

    <tr>
        <form action="AdminServlet">
            <td>
                <input name="productTitle" value="">
            </td>
            <td>
                <input name="productDescription" value="">
            </td>

            <td>
                <input name="productCategory" value="">
            </td>

            <td>
                <input name="productPrice" value="">
            </td>

            <td>
                <input name="productQuantity" value="">
            </td>

            <td>
                <input name="currentPage" type="hidden" value="adminProduct">
                <input name="productToAdd" type="hidden" value="">
                <input name="addProduct" type="submit" value="Add Product"/>
            </td>
        </form>
    </tr>

    <c:forEach items="${products}" var="product">
        <tr>
            <form action="AdminServlet">
                <td>
                    <input name="productTitle" value="${product.productTitle}">
                </td>
                <td>
                    <input name="productDescription" value="${product.description}">
                </td>
                <td>
                    <input name="productCategory" value="${product.category}">
                </td>

                <td>
                    <input name="productPrice" value="${product.price}">
                </td>

                <td>
                    <input name="productQuantity" value="${product.quantity}">
                </td>

                <td>
                    <input name="productId" type="hidden" value="${product.productId}">
                    <input name="currentPage" type="hidden" value="adminProduct">
                    <input name="productToDelete" type="hidden" value="${product.productId}">
                    <input name="deleteProduct" type="submit" value="Delete"/>
                    <input name="productToUpdate" type="hidden" value="${product.productId}">
                    <input name="updateProduct" type="submit" value="Update"/>
                </td>
            </form>
        </tr>
    </c:forEach>
</table>

</body>
</html>

