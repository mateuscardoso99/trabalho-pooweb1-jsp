<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<html>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<body>
    <%@ include file="/WEB-INF/components/menu.jsp" %>
    
    <div class="container">

        <div class="row">
            <c:if test="${not empty sessionScope.error}">
                <div class="alert alert-danger">${sessionScope.error}</div>
                <c:set var="error" scope="session" value=""></c:set>
            </c:if>
        </div>

        <div class="row mt-3">
            <form action="register" method="post">
                <h2 class="text-center bg-light text-dark">criar conta</h2>
    
                <div class="mb-3">
                    <label for="nome" class="form-label">Nome</label>
                    <input type="text" class="form-control" name="nome" id="nome">
                    <c:if test="${not empty sessionScope.validationErrors}">
                        <c:forEach items="${sessionScope.validationErrors.nome}" var="error">
                            <span class="text-danger">
                                ${error}
                            </span>
                        </c:forEach>
                    </c:if>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" name="email" id="email">
                    <c:if test="${not empty sessionScope.validationErrors}">
                        <c:forEach items="${sessionScope.validationErrors.email}" var="error">
                            <span class="text-danger">
                                ${error}
                            </span>
                        </c:forEach>
                    </c:if>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Senha</label>
                    <input type="password" class="form-control" name="password" id="password">
                    <c:if test="${not empty sessionScope.validationErrors}">
                        <c:forEach items="${sessionScope.validationErrors.senha}" var="error">
                            <span class="text-danger">
                                ${error}
                            </span>
                        </c:forEach>
                    </c:if>
                </div>
                <c:set var="validationErrors" scope="session" value=""></c:set>
                <button type="submit" class="btn btn-primary">salvar</button>
            </form>
        </div>
    </div>

    <%@ include file="/WEB-INF/components/footer.jsp" %>
</body>
</html>
