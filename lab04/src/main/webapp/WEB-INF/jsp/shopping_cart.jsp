<%@ page import="java.util.List" %>
<%@ page import="it.unipi.dsmt.jakartaee.lab04.dto.ShoppingCartDTO" %>
<%@ page import="it.unipi.dsmt.jakartaee.lab04.dto.BeerDTO" %><%--
  Created by IntelliJ IDEA.
  User: jose
  Date: 09/12/22
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Cart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<%
    ShoppingCartDTO shoppingCartDTO = (ShoppingCartDTO) request.getAttribute("shoppingCart");
    int numberProducts = (int) request.getAttribute("numberProducts");
%>
<body>
    <div class="container-sm px-4">
        <h4 class="d-flex justify-content-between align-items-center mt-5">
            <span class="text-primary">Your cart</span>
            <span class="badge bg-primary rounded-pill"><%= numberProducts %></span>
        </h4>
        <ul class="list-group mb-3">
            <% if (numberProducts == 0) { %>
                <li class="list-group-item d-flex justify-content-between">
                    <span>You have 0 products.</span>
                    <strong>--</strong>
                </li>
            <% } else { %>
            <%
                List<BeerDTO> products = shoppingCartDTO.getBeerDTOList();
                for(BeerDTO beerDTO: products) {
            %>
                <li class="list-group-item d-flex justify-content-between lh-sm">
                    <div>
                        <h6 class="my-0"><%= beerDTO.getName()%></h6>
                        <small class="text-muted"></small>
                    </div>
                    <input type="text" value="<%= beerDTO.getQuantity()%>"/>
                </li>
            <%  } %>
            <li class="list-group-item d-flex justify-content-between">
                <span>Total (USD)</span>
                <strong>--</strong>
            </li>
            <% } %>
        </ul>
        <div class="mb-2">
            <button type="submit" class="btn btn-primary">Clear</button>
            <button type="submit" class="btn btn-primary">Checkout</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/BeerSearchServlet">Back</a>
        </div>
    </div>
</body>
</html>
