<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<html>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<body>
    <%@ include file="/WEB-INF/components/menu.jsp" %>
    
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
        <input type="text" name="nome" id="nome" placeholder="nome">
        <input type="email" name="email" id="email" placeholder="email">
        <input type="password" name="password" id="password" placeholder="senha">
        <button type="submit">salvar</button>
    </form>
    <a href="login.jsp">login</a>
    <a href="index.jsp">inicio</a>
</body>
</html>
