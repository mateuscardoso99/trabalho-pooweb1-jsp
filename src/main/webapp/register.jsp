<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<html>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<body>
    <%@ include file="/WEB-INF/components/menu.jsp" %>
    
    <div class="container">
        <h1>criar conta</h1>

        <c:if test="${sessionScope.error != ''}">
            <p>${sessionScope.error}</p>
            <c:set var="error" scope="session" value=""></c:set>
        </c:if>

        <c:if test="${sessionScope.validationErrors != ''}">
            <ul>
                <c:forEach items="${sessionScope.validationErrors}" var="erro" varStatus="i">
                    <li>${erro}</li>
                </c:forEach>
            </ul>

            <c:set var="validationErrors" scope="session" value=""></c:set>
        </c:if>

        <form action="register" method="post">
            <div class="mb-3">
                <label for="nome" class="form-label">Nome</label>
                <input type="text" class="form-control" name="nome" id="nome">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" name="email" id="email">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Senha</label>
                <input type="password" class="form-control" name="password" id="password">
            </div>
            <button type="submit" class="btn btn-primary">salvar</button>
        </form>
    </div>
</body>
</html>
