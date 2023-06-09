<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<!DOCTYPE html>
<html lang="pt-br">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>

        <div class="container">
            <form action="${pageContext.request.contextPath}/user/link/gerenciar" method="post">
                <div class="mb-3">
                    <label for="url" class="form-label">Nome</label>
                    <input type="text" name="url" id="url" class="form-control">
                </div>
                <div class="mb-3">
                    <label for="descricao" class="form-label">Descrição</label>
                    <textarea name="descricao" class="form-control" id="descricao" cols="30" rows="10"></textarea>
                </div>
                <button type="submit" name="action" class="btn btn-success" value="cadastrar">Salvar link</button>
            </form>
        </div>
    </body>
</html>