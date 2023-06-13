<%@ include file="/WEB-INF/jspf/taglibs.jspf" %>

<!DOCTYPE html>
<html lang="pt-br">
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
    <body>
        <%@ include file="/WEB-INF/components/menu.jsp" %>

        <div class="container">
            <div class="row mb-3">
                <div class="col-3">
                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/user/contato/show">contatos</a>
                </div>
            </div>
            <div class="row">
                <div class="col-3">
                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/user/link/show">links</a>
                </div>
            </div>
        </div>

        <%@ include file="/WEB-INF/components/footer.jsp" %>
    </body>
</html>
