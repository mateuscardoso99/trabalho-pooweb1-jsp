<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<!DOCTYPE html>
<html lang="pt-br">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>

        <h1>olá ${sessionScope.usuario.nome}</h1>
        <a href="${pageContext.request.contextPath}/user/add-contato">novo contato</a>
        <a href="${pageContext.request.contextPath}/user/add-link">novo link</a>
    </body>
</html>
