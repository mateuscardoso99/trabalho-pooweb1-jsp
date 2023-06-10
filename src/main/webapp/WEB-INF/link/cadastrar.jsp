<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<!DOCTYPE html>
<html lang="pt-br">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>

        <div class="container">
            <div class="row mb-3">
                <c:if test="${not empty requestScope.error}">
                    <div class="alert alert-danger" role="alert">
                        ${requestScope.error}
                    </div>
                    <c:set var="error" scope="request" value=""></c:set>
                </c:if>
            </div>

            <div class="row">
                <form action="${pageContext.request.contextPath}/user/link/gerenciar" method="post">
                    <div class="mb-3">
                        <label for="url" class="form-label">URL</label>
                        <input type="text" name="url" id="url" class="form-control">
                        <c:if test="${not empty requestScope.validationErrors}">
                            <c:forEach items="${requestScope.validationErrors.url}" var="error">
                                <span class="text-danger">
                                    ${error}
                                </span>
                            </c:forEach>
                        </c:if>
                    </div>
                    <div class="mb-3">
                        <label for="descricao" class="form-label">Descrição</label>
                        <textarea name="descricao" class="form-control" id="descricao" cols="10" rows="10"></textarea>
                        <c:if test="${not empty requestScope.validationErrors}">
                            <c:forEach items="${requestScope.validationErrors.descricao}" var="error">
                                <span class="text-danger">
                                    ${error}
                                </span>
                            </c:forEach>
                        </c:if>
                    </div>
                    <c:set var="validationErrors" scope="request" value=""></c:set>
                    <button type="submit" name="action" class="btn btn-success" value="cadastrar">Salvar link</button>
                </form>
            </div>
        </div>
    </body>
</html>