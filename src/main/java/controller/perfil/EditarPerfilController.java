package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/perfil/salvar")
public class EditarPerfilController extends HttpServlet {

    //private UsuarioService usuarioService = new UsuarioService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");

        //usuarioService
        req.getRequestDispatcher("/WEB-INF/perfil/perfil.jsp").forward(req, resp);
    }

}
