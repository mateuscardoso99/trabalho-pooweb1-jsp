<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<!DOCTYPE html>
<html lang="pt-br">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>

        <div class="container">
            <a href="${pageContext.request.contextPath}/user/link/add">novo link</a>

            <table class="table">
                <thead>
                  <tr>
                    <th scope="col">URL</th>
                    <th scope="col">Descrição</th>
                    <th scope="col">Opções</th>
                  </tr>
                </thead>
                <tbody>
                    <c:forEach items="${links}" var="l">
                        <tr>
                          <td>${l.url}</td>
                          <td>${l.descricao}</td>
                          <td>
                            <button type="button" class="btn btn-sm btn-success" onclick="editar('${l.id}','${l.url}','${l.descricao}')">Editar</button>
                            <button type="button" class="btn btn-sm btn-danger" onclick="apagar(${l.id})">Apagar</button>
                          </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="modal fade" id="modalEditar" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Editar link</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/user/link/gerenciar" method="post">
                      <input type="hidden" name="idLink" id="idLinkEditar">
                      <div class="mb-3">
                        <label for="url" class="col-form-label">URL:</label>
                        <input type="text" class="form-control" name="url" id="url" placeholder="url">
                      </div>
                      <div class="mb-3">
                        <label for="descricao" class="col-form-label">Descrição:</label>
                        <textarea class="form-control" name="descricao" id="descricao" placeholder="descricao"></textarea>
                      </div>
                      <div class="mb-3">
                          <button type="submit" name="action" class="btn btn-success" value="editar">Salvar</button>
                      </div>
                    </form>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                  </div>
                </div>
              </div>
            </div>

            <div class="modal fade" id="modalApagar" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Apagar contato</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/user/link/gerenciar" method="post">
                      <input type="hidden" name="idLink" id="idLinkApagar">
                      <div class="mb-3">
                          <button type="submit" class="btn btn-danger" name="action" value="apagar">Apagar</button>
                      </div>
                    </form>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                  </div>
                </div>
              </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    </body>
</html>

<script type="text/javascript">
    function editar(id,url,descricao) {
        var myModal = new bootstrap.Modal(document.getElementById('modalEditar'), {});
        document.getElementById("idLinkEditar").value = id;
        document.getElementById("url").value = url;
        document.getElementById("descricao").value = descricao;
        myModal.show()
    }

    function apagar(id) {
        var myModal = new bootstrap.Modal(document.getElementById('modalApagar'), {});
        document.getElementById("idLinkApagar").value = id;
        myModal.show()
    }
</script>