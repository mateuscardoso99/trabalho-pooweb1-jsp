package controller.contato;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Contato;
import service.ContatoService;

@WebServlet("/user/gerenciar-contato")
public class GerenciarContatoController extends HttpServlet {

    private ContatoService contatoService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String nome = req.getParameter("nome");
        String telefone = req.getParameter("telefone");

        if(action.equals("cadastrar")){
            Contato contato = new Contato(nome, telefone);
            contatoService.salvar(req, contato);
        }
        else if(action.equals("editar")){
            Contato contato = new Contato(nome, telefone);
            contatoService.atualizar(req, contato);
        }
        else if(action.equals("apagar")){
            Long idContato = Long.valueOf(req.getParameter("idContato"));
            contatoService.deletar(req, idContato);
        }
        
        resp.sendRedirect(req.getContextPath()+"/user/contato/show");

    }
    
}
