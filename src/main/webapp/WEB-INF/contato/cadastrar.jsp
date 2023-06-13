<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<!DOCTYPE html>
<html lang="pt-br">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>

        <div class="container">
            <div class="row">
                <nav aria-label="breadcrumb">
                    <ol class="breadcrumb">
                      <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/user/contato/show">Contato</a></li>
                      <li class="breadcrumb-item active" aria-current="page">Cadastrar</li>
                    </ol>
                  </nav>
            </div>

            <div class="row mb-3">
                <c:if test="${not empty requestScope.error}">
                    <div class="alert alert-danger" role="alert">
                        ${requestScope.error}
                    </div>
                    <c:set var="error" scope="request" value=""></c:set>
                </c:if>
            </div>

            <div class="row">
                <form action="${pageContext.request.contextPath}/user/contato/gerenciar" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="nome" class="form-label">Nome</label>
                        <input type="text" class="form-control" name="nome" id="nome">
                        <c:if test="${not empty requestScope.validationErrors}">
                            <c:forEach items="${requestScope.validationErrors.nome}" var="error">
                                <span class="text-danger">
                                    ${error}
                                </span>
                            </c:forEach>
                        </c:if>
                    </div>
                    <div class="mb-3">
                        <label for="telefone" class="form-label">Telefone</label>
                        <input type="tel" class="form-control" name="telefone" id="telefone">
                        <c:if test="${not empty requestScope.validationErrors}">
                            <c:forEach items="${requestScope.validationErrors.telefone}" var="error">
                                <span class="text-danger">
                                    ${error}
                                </span>
                            </c:forEach>
                        </c:if>
                    </div>
                    <div class="mb-3">
                        <label for="foto" class="form-label">Foto</label>
                        <input type="file" class="form-control" name="foto" id="foto">
                    </div>
                    <c:set var="validationErrors" scope="request" value=""></c:set>
                    <button type="submit" name="action" class="btn btn-success" value="cadastrar">Salvar contato</button>
                </form>
            </div>
        </div>

        <%@ include file="/WEB-INF/components/footer.jsp" %>
    </body>
</html>