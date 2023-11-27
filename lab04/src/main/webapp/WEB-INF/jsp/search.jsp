<%@ page import="java.util.List" %>
<%@ page import="it.unipi.dsmt.jakartaee.lab04.dto.BeerDTO" %><%--
  Created by IntelliJ IDEA.
  User: jose
  Date: 30/11/22
  Time: 00:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Beers</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }

      .b-example-divider {
        height: 3rem;
        background-color: rgba(0, 0, 0, .1);
        border: solid rgba(0, 0, 0, .15);
        border-width: 1px 0;
        box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
      }

      .b-example-vr {
        flex-shrink: 0;
        width: 1.5rem;
        height: 100vh;
      }

      .bi {
        vertical-align: -.125em;
        fill: currentColor;
      }

      .nav-scroller {
        position: relative;
        z-index: 2;
        height: 2.75rem;
        overflow-y: hidden;
      }

      .nav-scroller .nav {
        display: flex;
        flex-wrap: nowrap;
        padding-bottom: 1rem;
        margin-top: -1px;
        overflow-x: auto;
        text-align: center;
        white-space: nowrap;
        -webkit-overflow-scrolling: touch;
      }
      .product_img {
        display: block;
        margin: auto;
        width: 100px;
        height: 237px;
        object-fit:contain;
      }
    </style>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
  <script type="text/javascript">
    function addShoppingCart(productId, productName){
        params = {action: 'add-product', productId: productId, productName: productName};
        $.post("${pageContext.request.contextPath}/ShoppingCartServlet", params, function(result){
          $("#counter").text(result);
        }).fail(function(xhr, status, error) {
          alert('error');
        });
    }
  </script>
</head>
<body>
<%
  List<BeerDTO> beerDTOList = (List<BeerDTO>) request.getAttribute("beers");
  String search = request.getAttribute("search") != null ? request.getAttribute("search").toString() : "";
  String fromPrice = "";
  String toPrice = "";
  boolean hasResults = beerDTOList != null && !beerDTOList.isEmpty();
%>
<header>
  <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">La Torre del Luppolo</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav me-auto mb-2 mb-md-0">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Another link here</a>
          </li>
          <li class="nav-item">
            <a class="nav-link disabled">Disabled link</a>
          </li>
        </ul>
        <form class="d-flex" role="search" method="post" action="${pageContext.request.contextPath}/BeerSearchServlet">
          <input class="form-control me-2" type="search" name="search" placeholder="Search" aria-label="Search" value="<%= search%>">
          <input class="form-control me-2" type="search" name="fromPrice" placeholder="From price" aria-label="From price" value="<%= fromPrice%>">
          <input class="form-control me-2" type="search" name="toPrice" placeholder="To Price" aria-label="To price" value="<%= toPrice%>">
          <button class="btn btn-outline-success" type="submit">Search</button>
        </form>
      </div>
    </div>
  </nav>
</header>
  <main>
    <section class="py-5 text-center container">
      <div class="row py-lg-5">
        <div class="col-lg-6 col-md-8 mx-auto">
          <h1 class="fw-light">Servlet + JSP: Beer Search Page</h1>
          <p>
            <a href="${pageContext.request.contextPath}/BeerManagerServlet?action=new" class="btn btn-primary my-2">Add new beer</a>
            <a href="${pageContext.request.contextPath}/ShoppingCartServlet?action=view" class="btn btn-secondary my-2">View shopping cart <span id="counter"></span></a>
          </p>
        </div>
      </div>
    </section>

    <div class="album py-5 bg-light">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <% if (hasResults) { %>
            <% for(BeerDTO beer: beerDTOList) { %>
            <div class="col">
              <div class="card shadow-sm">
                <img class="product_img" src="<%= beer.getImageUrl()%>" />
                <div class="card-body">
                  <p class="card-text"><%= beer.getName() %></p>
                  <div class="d-flex justify-content-between align-items-center">
                    <div class="btn-group">
                      <button type="button" class="btn btn-sm btn-outline-secondary">View</button>
                      <button type="button" onclick='addShoppingCart("<%= beer.getId() %>", "<%= beer.getName() %>");' class="btn btn-sm btn-outline-secondary">Add to cart</button>
                    </div>
                    <small class="text-muted">Rating: <%= beer.getRating() %></small>
                  </div>
                </div>
              </div>
            </div>
            <% } %>
          <% } else { %>
            <p>No results found.</p>
          <% } %>
        </div>
      </div>
    </div>

  </main>
</body>
</html>
