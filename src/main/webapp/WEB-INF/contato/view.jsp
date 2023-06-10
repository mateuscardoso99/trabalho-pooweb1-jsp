<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<!DOCTYPE html>
<html lang="pt-br">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>

        <div class="container">
            <a href="${pageContext.request.contextPath}/user/contato/add">novo contato</a>

            <table class="table">
                <thead>
                  <tr>
                    <th scope="col">Nome</th>
                    <th scope="col">Telefone</th>
                    <th scope="col">Opções</th>
                  </tr>
                </thead>
                <tbody>
                    <c:forEach items="${contatos}" var="contato">
                        <tr>
                          <td>${contato.nome}</td>
                          <td>${contato.telefone}</td>
                          <td>
                            <button type="button" class="btn btn-sm btn-success" onclick="editar('${contato.id}','${contato.nome}','${contato.telefone}')">Editar</button>
                            <button type="button" class="btn btn-sm btn-danger" onclick="apagar('${contato.id}')">Apagar</button>
                          </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="modal fade" id="modalEditar" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Editar contato</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/user/contato/gerenciar" method="post">
                      <input type="hidden" name="idContato" id="idContatoEditar">
                      <div class="mb-3">
                        <label for="nome" class="col-form-label">Nome:</label>
                        <input type="text" class="form-control" name="nome" id="nome" placeholder="nome">
                      </div>
                      <div class="mb-3">
                        <label for="telefone" class="col-form-label">Telefone:</label>
                        <input type="tel" class="form-control" name="telefone" id="telefone" placeholder="telefone">
                      </div>
                      <div class="mb-3">
                          <button type="submit" name="action" class="btn btn-success" value="editar">Salvar contato</button>
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
                    <form action="${pageContext.request.contextPath}/user/contato/gerenciar" method="post">
                      <input type="hidden" name="idContato" id="idContatoApagar">
                      <div class="mb-3">
                          <button type="submit" class="btn btn-danger" name="action" value="apagar">Apagar contato</button>
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
    function editar(id,nome,telefone) {
        var myModal = new bootstrap.Modal(document.getElementById('modalEditar'), {});
        document.getElementById("idContatoEditar").value = id;
        document.getElementById("nome").value = nome;
        document.getElementById("telefone").value = telefone;
        myModal.show()
    }

    function apagar(id) {
        var myModal = new bootstrap.Modal(document.getElementById('modalApagar'), {});
        document.getElementById("idContatoApagar").value = id;
        myModal.show()
    }
</script>