<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<html>
    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>
        
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
            <input type="text" name="email" id="email" placeholder="email">
            <input type="password" name="password" id="password" placeholder="senha">
            <button type="submit">Entrar</button>
        </form>
        <a href="register.jsp">criar conta</a>
        <a href="index.jsp">inicio</a>
    </body>
</html>
