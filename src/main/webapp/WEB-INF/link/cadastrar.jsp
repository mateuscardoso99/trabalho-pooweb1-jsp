<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<!DOCTYPE html>
<html lang="pt-br">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>

        <form action="${pageContext.request.contextPath}/user/gerenciar-link" method="post">
            <textarea name="url" id="url" cols="30" rows="10"></textarea>
            <textarea name="descricao" id="" cols="30" rows="10"></textarea>
            <button type="submit" name="action" value="cadastrar">Salvar link</button>
        </form>
    </body>
</html>