<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<!DOCTYPE html>
<html lang="pt-br">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>

        <form action="${pageContext.request.contextPath}/user/gerenciar-contato" method="post">
            <input type="text" name="nome" id="nome" placeholder="nome">
            <input type="tel" name="telefone" id="telefone" placeholder="telefone">
            <button type="submit" name="action" value="cadastrar">Salvar contato</button>
        </form>
    </body>
</html>