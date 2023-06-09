<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<!DOCTYPE html>
<html lang="pt-br">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>

        <table class="table">
            <thead>
              <tr>
                <th scope="col">Nome</th>
                <th scope="col">Telefone</th>
                <th scope="col">Opções</th>
              </tr>
            </thead>
            <tbody>
                <c:forEach items="${links}" var="l">
                    <tr>
                      <td>${l.url}</td>
                      <td>${l.descricao}</td>
                      <td>
                        <button type="button" class="btn btn-small btn-success">Editar</button>
                        <button type="button" class="btn btn-small btn-danger">Apagar</button>
                      </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>