<%@ page import="ui.TestServlet" %><%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
  <p>
    tim sak <%= (new java.util.Date()).toLocaleString()%>
    tt <%= (new ui.Del()).getA() %>
    <form action="TestServlet">
      <input name="testInput" />
    <input name="testInput2" />
      <input type="submit" />
  </form>
  </p>
  </body>
</html>