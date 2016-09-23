<%@ page import="ui.TestServlet" %><%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
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
<style>
    .shoppingItem {
        float: left;
        margin: 10px;
        padding: 10px;
        max-width: 250px;
        height: 100px;
        border: 1px solid black;
    }
</style>


<body>
tim sak <%= (new java.util.Date()).toLocaleString()%>
<table style="width:100%">
     
    <tr>
           
        <th>ItemTitle</th>
           
        <th>Description</th>
           
        <th>Price</th>
           
        <th></th>
         
    </tr>


    <% for (int i = 0; i < 4; i++) { %>
    <tr>
        <td>Item <%= i %>
        </td>
        <td>Basic description</td>
        <td>$ <%= 10 * i %>
        </td>
        <td>
            <form action="TestServlet">
                <input name="addToCart" type="submit" value="Add To Cart"/>
            </form>
        </td>

    </tr>
    <% } %>


</table>

<div class="shoppingCart">
    <h2>Shopping Cart</h2>
    <table style="width: 250px;">
         
        <tr>
               
            <th>ItemTitle</th>
               
            <th>Price</th>
               
            <th></th>
             
        </tr>


        <% for (int i = 0; i < 4; i++) { %>
        <tr>
            <td>Item <%= i %>
            </td>
            <td>$ <%= 10 * i %>
            </td>
            <td>
                <form action="TestServlet">
                    <input type="button" value="Remove Item"/>
                </form>
            </td>

        </tr>
        <% } %>


    </table>
</div>

<form action="TestServlet">
    <input name="testInput"/>
    <input name="testInput2"/>
    <input type="submit"/>
</form>
<form action="TestServlet">
    <input name="showInventory" type="submit" value="all">
</form>
</p>
</body>
</html>