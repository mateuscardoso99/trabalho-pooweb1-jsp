<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<html>
    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>
        
        <div class="container">
            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-danger" role="alert">
                    ${requestScope.error}
                </div>
                <c:set var="error" scope="request" value=""></c:set>
            </c:if>

            <c:if test="${not empty sessionScope.success}">
                <div class="alert alert-success" role="alert">
                    ${sessionScope.success}
                </div>
                <c:set var="success" scope="session" value=""></c:set>
            </c:if>

            <form action="login" method="post">
                <h2>login</h2>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="text" class="form-control" name="email" id="email">
                    <c:if test="${not empty requestScope.validationErrors}">
                        <c:forEach items="${requestScope.validationErrors.email}" var="error">
                            <span class="text-danger">
                                ${error}
                            </span>
                        </c:forEach>
                    </c:if>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Senha</label>
                    <input type="password" class="form-control" name="password" id="password">
                    <c:if test="${not empty requestScope.validationErrors}">
                        <c:forEach items="${requestScope.validationErrors.senha}" var="error">
                            <span class="text-danger">
                                ${error}
                            </span>
                        </c:forEach>
                    </c:if>
                </div>
                <c:set var="validationErrors" scope="request" value=""></c:set>
                <button type="submit" class="btn btn-primary">Entrar</button>
            </form>
        </div>
    </body>
</html>
