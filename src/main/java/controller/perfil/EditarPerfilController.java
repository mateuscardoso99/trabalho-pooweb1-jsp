package controller.perfil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Usuario;
import service.UsuarioService;

@WebServlet("/user/perfil/salvar")
public class EditarPerfilController extends HttpServlet {

    private UsuarioService usuarioService = new UsuarioService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String senha = req.getParameter("password");

        Usuario usuario = new Usuario(nome, email, senha);

        if(usuarioService.atualizar(req, usuario))
            req.getSession().setAttribute("success","perfil atualizado com sucesso");
        else
            req.getSession().setAttribute("error","erro ao atualizar perfil");
        

        resp.sendRedirect(req.getContextPath()+"/user/perfil");
    }

}
