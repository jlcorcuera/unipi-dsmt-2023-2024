<%--
  Created by IntelliJ IDEA.
  User: jose
  Date: 26/11/23
  Time: 23:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DSMT: Lab 04 - Input Text</title>
</head>
<%
    int n = 10;
%>

<body>
    <h1>Example of HTML dynamic generation</h1>
    <% for(int i = 0; i < n; i++) {%>
        <label for="<%= "id"+i %>" >Input <%= i%></label>
        <input id="<%= "id"+i %>" type="text" value="<%= "Value here " + i%>"/>
        <br/>
    <%} %>
</body>
</html>
