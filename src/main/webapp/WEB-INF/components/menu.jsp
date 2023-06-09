<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
      <!-- <a class="navbar-brand" href="#">Navbar</a> -->
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <c:choose>
            <c:when test="${sessionScope.logado != '' && sessionScope.logado == 'true'}">
              <li class="nav-item">
                <a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/user">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/user/perfil">Perfil</a>
              </li>
              <li class="nav-item">
                <a class="nav-link btn btn-dark text-light" href="${pageContext.request.contextPath}/logout">Sair</a>
              </li>
            </c:when>
            <c:otherwise>
              <li class="nav-item">
                <a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" aria-current="page" href="login.jsp">Login</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="register.jsp">Criar conta</a>
              </li>
            </c:otherwise>
          </c:choose>
        </ul>
      </div>
    </div>
  </nav>