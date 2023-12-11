<%--
  Created by IntelliJ IDEA.
  User: jose
  Date: 11/12/2022
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lab 06: Producer/Consumer JMS message</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<div class="container-sm px-4">
    <!-- Content here -->
    <h3 class="display-2 mt-5">Contact Us - JMS</h3>
    <form class="row g-3 needs-validation" novalidate method="post" action="${pageContext.request.contextPath}/ContactUsServlet">
        <div class="mb-3">
            <label for="emailInput" class="form-label">Email address</label>
            <input type="email" class="form-control" id="emailInput" name="email" placeholder="name@example.com">
        </div>
        <div class="mb-3">
            <label for="commentsTextArea" class="form-label">Comments</label>
            <textarea class="form-control" id="commentsTextArea" name="comments" rows="3"></textarea>
        </div>
        <div class="mb-2">
            <button type="submit" class="btn btn-primary">Send</button>
        </div>
    </form>
</div>
</body>
</html>