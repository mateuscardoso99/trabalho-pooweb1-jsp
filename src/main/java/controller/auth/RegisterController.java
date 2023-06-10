package controller.auth;

import service.UsuarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Usuario;

import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private UsuarioService usuarioService = new UsuarioService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String senha = req.getParameter("password");

        Usuario u = new Usuario(nome, email, senha);

        if(usuarioService.cadastrar(u, req)){
            req.getSession().setAttribute("success","conta criada com sucesso");
            resp.sendRedirect(req.getContextPath()+"/login");
        }
        else{
            resp.sendRedirect("register.jsp");
        }    
    }
}
