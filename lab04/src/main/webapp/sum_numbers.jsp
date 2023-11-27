<%--
  Created by IntelliJ IDEA.
  User: jose
  Date: 2023-11-27
  Time: 4:03 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        int n = 10;
        String buffer = "";
        double currentSum = 0;
    %>
    <% for(int i = 1; i <= n; i++) {
        buffer = buffer + (i > 1 ? "+ " : "" ) + i;
        currentSum = currentSum + i;
    %>
    <h1><%= buffer%> = <%= currentSum%></h1>
    <% } %>
</body>
</html>
