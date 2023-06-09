<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<html>
    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>
        
        <div class="container">
            <h2>login</h2>

            <c:if test="${sessionScope.error != ''}">
                <p>${sessionScope.error}</p>
                <c:set var="error" scope="session" value=""></c:set>
            </c:if>

            <c:if test="${sessionScope.success != ''}">
                <p>${sessionScope.success}</p>
                <c:set var="success" scope="session" value=""></c:set>
            </c:if>

            <form action="login" method="post">
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="text" class="form-control" name="email" id="email">
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Senha</label>
                    <input type="password" class="form-control" name="password" id="password">
                </div>
                <button type="submit" class="btn btn-primary">Entrar</button>
            </form>
        </div>
    </body>
</html>
