<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
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
    Calendar calendar = Calendar.getInstance();
    int currentMinutes = calendar.get(Calendar.MINUTE);
    SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    String currentDateTime = SDF.format(new Date());
%>

<body>
    <h1>Current time <%= currentDateTime%></h1>
    <% if (currentMinutes % 2 == 0) {%>
        <h1>¡Bienvenido al curso de DSMT!</h1>
    <%} else { %>
        <h1>Benvenuto al corso di DSMT!</h1>
    <% } %>
</body>
</html>
