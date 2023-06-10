package controller.link;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Link;
import service.LinkService;

@WebServlet("/user/link/gerenciar")
public class GerenciarLinkController extends HttpServlet {
    private LinkService linkService = new LinkService();

    //método service responde tanto get como post
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String idLink = req.getParameter("idLink");
        String url = req.getParameter("url");
        String descricao = req.getParameter("descricao");

        if(action.equals("cadastrar")){
            Link Link = new Link(url, descricao);

            if(linkService.salvar(req, Link)){
                req.getSession().setAttribute("success","link criado com sucesso");
                resp.sendRedirect(req.getContextPath()+"/user/link/show");
            }
            else{
                req.getRequestDispatcher("/WEB-INF/link/cadastrar.jsp").forward(req, resp);
            }
        }
        else if(action.equals("editar")){
            Link link = new Link(url, descricao);

            if(linkService.atualizar(req, link, idLink))
                req.getSession().setAttribute("success","link atualizado com sucesso");
            
            resp.sendRedirect(req.getContextPath()+"/user/link/show");
        }
        else if(action.equals("apagar")){        
            if(linkService.deletar(req, idLink))
                req.getSession().setAttribute("success","link excluido com sucesso");
            
            resp.sendRedirect(req.getContextPath()+"/user/link/show");
        }
        else{
            req.getSession().setAttribute("error", "ação inválida");
            resp.sendRedirect(req.getContextPath()+"/user/link/show");
        }
    }
}
