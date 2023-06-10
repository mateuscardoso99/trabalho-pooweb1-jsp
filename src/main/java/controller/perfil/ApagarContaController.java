package controller.perfil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UsuarioService;

@WebServlet("/user/apagar-conta")
public class ApagarContaController extends HttpServlet {
    private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        usuarioService.apagarConta(req);
        resp.sendRedirect(req.getContextPath()+"/logout");
    }
    
}
