package controller.documento;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Documento;
import model.Usuario;
import service.DocumentoService;
import utils.BuscarUsuarioLogado;

@WebServlet("/user/docs/show")
public class ViewDocumentosController extends HttpServlet{
    private DocumentoService documentoService = new DocumentoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Documento> documentos = documentoService.findAll(req);
        Usuario usuarioLogado = BuscarUsuarioLogado.getUsuarioLogado(req);
        usuarioLogado.setDocumentos(documentos);
        req.getSession().setAttribute("usuario", usuarioLogado);
        req.setAttribute("documentos", documentos);
        req.getRequestDispatcher("/WEB-INF/documento/view.jsp").forward(req, resp);
    }
}
