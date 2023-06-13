package controller.contato;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Contato;
import model.Usuario;
import service.ContatoService;
import utils.BuscarUsuarioLogado;

@WebServlet("/user/contato/show")
public class VisualizarContatoController extends HttpServlet {
    private ContatoService contatoService = new ContatoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Contato> contatos = contatoService.findAll(req);
        Usuario usuarioLogado = BuscarUsuarioLogado.getUsuarioLogado(req);
        usuarioLogado.setContatos(contatos);
        req.getSession().setAttribute("usuario", usuarioLogado);
        req.setAttribute("contatos", contatos);
        req.getRequestDispatcher("/WEB-INF/contato/view.jsp").forward(req, resp);
    }
}
