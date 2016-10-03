<%--
  Created by IntelliJ IDEA.
  User: cj
  Date: 2016-09-29
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>Login</h2>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:out value="${loginFailed}" />
<form action="LoginServlet">
    <input name="lastPage" type="hidden" value="${lastPage}">
    <input name="redirect" type="hidden" value="${redirect}">
    <input name="username" type="" value="">
    <input name="password" type="" value="">
    <input name="loginFields" type="submit" value="Login"/>
</form>

</body>
</html>
