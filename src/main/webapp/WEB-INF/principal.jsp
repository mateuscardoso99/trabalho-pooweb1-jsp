<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<!DOCTYPE html>
<html lang="pt-br">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>

        <h1>olá ${sessionScope.usuario.nome}</h1>
        <a href="/trabalho/logout">sair</a>
    </body>
</html>
