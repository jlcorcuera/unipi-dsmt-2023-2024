<%--
  Created by IntelliJ IDEA.
  User: jose
  Date: 09/12/22
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Beer</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
  <div class="container-sm px-4">
    <!-- Content here -->
    <h1 class="display-1 mt-5">New beer form</h1>
    <form class="row g-3 needs-validation" novalidate method="post" action="${pageContext.request.contextPath}/BeerManagerServlet">
      <input type="hidden" name="action" value="create">
      <div class="mb-2">
        <label for="nameInput" class="form-label">Name</label>
        <input type="text" required class="form-control" id="nameInput" name="name">
        <div class="invalid-feedback">
          Please enter a name.
        </div>
      </div>
      <div class="mb-2 form-url">
        <label for="imageInput" class="form-label">Image URL</label>
        <input type="url" required class="form-control" id="imageInput" name="imageUrl">
        <div class="invalid-feedback">
          Please enter an image URL.
        </div>
      </div>
      <div class="mb-2 form-url">
        <label for="ratingInput" class="form-label">Rating</label>
        <input type="number" required class="form-control" id="ratingInput" name="rating">
        <div class="invalid-feedback">
          Please enter a rating.
        </div>
      </div>
      <div class="mb-2">
        <button type="submit" class="btn btn-primary">Create</button>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/BeerSearchServlet">Cancel</a>
      </div>
    </form>
  </div>
  <script type="text/javascript">
    (() => {
      'use strict'
      // Fetch all the forms we want to apply custom Bootstrap validation styles to
      const forms = document.querySelectorAll('.needs-validation')
      // Loop over them and prevent submission
      Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
          if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
          }
          form.classList.add('was-validated');
        }, false)
      })
    })()
  </script>
</body>
</html>
