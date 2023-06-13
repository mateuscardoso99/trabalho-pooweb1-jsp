package controller.link;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Link;
import model.Usuario;
import service.LinkService;
import utils.BuscarUsuarioLogado;

@WebServlet("/user/link/show")
public class ViewLinkController extends HttpServlet{
    private LinkService linkService = new LinkService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Link> links = linkService.findAll(req);
        Usuario usuarioLogado = BuscarUsuarioLogado.getUsuarioLogado(req);
        usuarioLogado.setLinks(links);
        req.getSession().setAttribute("usuario", usuarioLogado);
        req.setAttribute("links", links);
        req.getRequestDispatcher("/WEB-INF/link/view.jsp").forward(req, resp);
    }
}
