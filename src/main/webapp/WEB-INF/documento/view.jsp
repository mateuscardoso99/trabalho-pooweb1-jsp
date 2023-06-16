<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-br">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>

        <div class="container">
            <c:if test="${not empty sessionScope.success}">
              <div class="row mb-3">
                <div class="alert alert-success" role="alert">
                  ${sessionScope.success}
                </div>
                <c:set var="success" scope="session" value=""></c:set>
              </div>
            </c:if>

            <c:if test="${not empty sessionScope.error}">
              <div class="row mb-3">
                  <div class="alert alert-danger" role="alert">
                      ${sessionScope.error}
                  </div>
                  <c:set var="error" scope="session" value=""></c:set>
              </div>
            </c:if>

            <c:if test="${not empty sessionScope.validationErrors}">
              <c:if test="${not empty sessionScope.validationErrors.arquivo}">
                <div class="row mb-3">
                  <c:forEach items="${sessionScope.validationErrors.arquivo}" var="error">
                    <div class="alert alert-danger" role="alert">${error}</div>
                  </c:forEach>
                </div>
              </c:if>
            </c:if>

            <c:set var="validationErrors" scope="session" value=""></c:set>

            <div class="row m-3">
              <div class="col text-center">
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalAddArquivo">
                    novo arquivo
                </button>
              </div>
            </div>

            <div class="row">
                <c:choose>
                  <c:when test="${documentos.size() > 0}">
                    <c:forEach items="${documentos}" var="doc">
                      <div class="col-md-6 text-left mt-5 mb-5">
                        <iframe loading="lazy" title="preview" src="${pageContext.request.contextPath}/docs?name=${doc.arquivo}" width="100%" height="250"></iframe>
                        <a class="btn btn-danger" href="${pageContext.request.contextPath}/user/docs/file?idDoc=${doc.id}&action=apagar">Apagar</a>
                        <a class="btn btn-success" href="${pageContext.request.contextPath}/user/docs/file?name=${doc.arquivo}&action=baixar">Baixar</a>
                      </div>
                    </c:forEach>
                  </c:when>
                  <c:otherwise>
                    <p class="text-danger fw-bold text-center">Nenhum documento cadastrado</p>
                  </c:otherwise>
                </c:choose>
            </div>

            <div class="modal fade" id="modalAddArquivo" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Upload arquivo</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/user/docs/salvar" method="post" enctype="multipart/form-data">
                      <div class="mb-3">
                        <label for="arquivo" class="col-form-label">Arquivo:</label>
                        <input type="file" class="form-control" name="arquivo" id="arquivo" placeholder="arquivo">
                      </div>
                      <div class="mb-3">
                          <button type="submit" class="btn btn-success">Salvar</button>
                      </div>
                    </form>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                  </div>
                </div>
              </div>
            </div>

            <!-- <div class="modal fade" id="modalApagar" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Apagar contato</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <div class="row">
                      <p>deseja apagar este contato?</p>
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <form action="${pageContext.request.contextPath}/user/contato/gerenciar" method="post">
                      <input type="hidden" name="idContato" id="idContatoApagar">
                      <button type="submit" class="btn btn-danger" name="action" value="apagar">Apagar contato</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>             -->
        </div>

        <%@ include file="/WEB-INF/components/footer.jsp" %>
    </body>
</html>