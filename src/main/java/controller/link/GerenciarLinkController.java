package controller.link;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Link;
import service.LinkService;

@WebServlet("/user/gerenciar-link")
public class GerenciarLinkController extends HttpServlet {
    private LinkService linkService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String url = req.getParameter("url");
        String descricao = req.getParameter("descricao");

        if(action.equals("cadastrar")){
            Link Link = new Link(url, descricao);
            linkService.salvar(req, Link);
        }
        else if(action.equals("editar")){
            Link Link = new Link(url, descricao);
            linkService.atualizar(req, Link);
        }
        else if(action.equals("apagar")){
            Long idLink = Long.valueOf(req.getParameter("idLink"));
            linkService.deletar(req, idLink);
        }
        
        resp.sendRedirect(req.getContextPath()+"/user/contato/show");

    }
}
