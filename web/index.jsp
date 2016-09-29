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
    <input type="submit" value="Step Forward">
</form>
<form action="AdminServlet" method="get">
    <input name="destination" value="AdminPage">
    <input type="submit" value="AdminPage">
</form>
</body>
</html>