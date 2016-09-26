<%@ page import="java.util.Collection" %>
<%@ page import="ui.ProductInfo" %><%--
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


<table style="width:100%">
     
    <tr>
           
        <th>ItemTitle</th>
           
        <th>Description</th>
           
        <th>Price</th>
           
        <th></th>
         
    </tr>

    <% Collection<ProductInfo> prods = (Collection<ProductInfo>)request.getAttribute("products");
    for (ProductInfo p :prods ) { %>

    <tr>
        <td><%= p.getProductTitle() %>
        </td>
        <td><%= p.getDescription() %> </td>
        <td>$ <%= p.getPrice() %>
        </td>
        <td>
            <form action="TestServlet">
                <input name="productToBuy" type="hidden" value=<%= p.getProductId() %>>
                <input name="addToCart" type="submit" value="Add To Cart"/>
            </form>
        </td>

    </tr>
    <% } %>


</table>

    </body>
</html>
