package controller.contato;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ContatoService;

@WebServlet("/user/contato/gerenciar")
@MultipartConfig(
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 5 * 5
)
public class GerenciarContatoController extends HttpServlet {

    private ContatoService contatoService = new ContatoService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if(action.equals("cadastrar")){
            if(contatoService.salvar(req)){
                req.getSession().setAttribute("success","contato criado com sucesso");
                resp.sendRedirect(req.getContextPath()+"/user/contato/show");
            }
            else{
                req.getRequestDispatcher("/WEB-INF/contato/cadastrar.jsp").forward(req, resp);
            }
        }
        else if(action.equals("editar")){
            if(contatoService.atualizar(req))
                req.getSession().setAttribute("success","contato atualizado com sucesso");
            
            resp.sendRedirect(req.getContextPath()+"/user/contato/show");
        }
        else if(action.equals("apagar")){
            if(contatoService.deletar(req))
                req.getSession().setAttribute("success","contato excluido com sucesso");
            
            resp.sendRedirect(req.getContextPath()+"/user/contato/show");
        }
        else{
            req.getSession().setAttribute("error", "ação inválida");
            resp.sendRedirect(req.getContextPath()+"/user/contato/show");
        }
    }
    
}
