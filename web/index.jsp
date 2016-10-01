<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>

<body>

<h1 style="align-content: center">
    Welcome
</h1>

<form action="ClientServlet" method="get">
    <input type="submit" value="Customer">
</form>

<form action="AdminServlet" method="get">
    <input name="redirect" type="hidden" value="goToIndex">
    <input type="submit" value="Admin">
</form>

<form action="EmployeeServlet" method="get">
    <input name="redirect" type="hidden" value="goToIndex">
    <input type="submit" value="Employee">
</form>

<form action="ClientServlet" method="get">
    <input name="clearCookies" type="hidden" value="clear">
    <input type="submit" value="clear cookies">
</form>
</body>
</html>