package controller.link;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.LinkService;

@WebServlet("/user/link/gerenciar")
public class GerenciarLinkController extends HttpServlet {
    private LinkService linkService = new LinkService();

    //método service responde tanto get como post
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if(action.equals("cadastrar")){
            if(linkService.salvar(req)){
                req.getSession().setAttribute("success","link criado com sucesso");
                resp.sendRedirect(req.getContextPath()+"/user/link/show");
            }
            else{
                req.getRequestDispatcher("/WEB-INF/link/cadastrar.jsp").forward(req, resp);
            }
        }
        else if(action.equals("editar")){
            if(linkService.atualizar(req))
                req.getSession().setAttribute("success","link atualizado com sucesso");
            
            resp.sendRedirect(req.getContextPath()+"/user/link/show");
        }
        else if(action.equals("apagar")){        
            if(linkService.deletar(req))
                req.getSession().setAttribute("success","link excluido com sucesso");
            
            resp.sendRedirect(req.getContextPath()+"/user/link/show");
        }
        else{
            req.getSession().setAttribute("error", "ação inválida");
            resp.sendRedirect(req.getContextPath()+"/user/link/show");
        }
    }
}
