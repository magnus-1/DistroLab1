<%--
  Created by IntelliJ IDEA.
  User: cj
  Date: 2016-09-30
  Time: 09:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
</head>
<body>
<h2>Admin</h2>
<form action="AdminServlet" method="get">
    <input name="redirect" type="hidden" value="goToProducts">
    <input type="submit" value="Go To Products">
</form>
<form action="AdminServlet" method="get">
    <input name="redirect" type="hidden" value="goToUsers">
    <input type="submit" value="Go To Users">
</form>
</body>
</html>
