<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<!DOCTYPE html>
<html lang="pt-br">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>

        <div class="container">
            <h1>olá ${sessionScope.usuario.nome}</h1>
            <a href="${pageContext.request.contextPath}/user/contato/show">contatos</a>
            <a href="${pageContext.request.contextPath}/user/link/show">links</a>
        </div>
    </body>
</html>
