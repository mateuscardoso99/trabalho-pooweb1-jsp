package controller.auth;

import service.UsuarioService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Usuario;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private UsuarioService usuarioService = new UsuarioService();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
        dispatcher.forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter("email");
        String senha = req.getParameter("password");

        Usuario u = new Usuario(email, senha);
        
        System.out.println("post /login");
        
        if(usuarioService.autenticar(u, req))
            resp.sendRedirect(req.getContextPath()+"/user");
        else
            req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

}
