<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: jose
  Date: 30/11/22
  Time: 01:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>People Directory</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<%
  String username = (String) session.getAttribute("username");
  List<String> peopleDirectory = (List<String>) application.getAttribute("peopleDirectory");
%>
<body>
  <ul class="list-group">
    <% for(String people: peopleDirectory) { %>
        <li class="list-group-item <%= (people.equals(username)) ? "active": "" %>" aria-current="true"><%= people %></li>
    <% } %>
  </ul>
</body>
</html>
