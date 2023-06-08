package controller;

import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private LoginService loginService = new LoginService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter("email");
        String senha = req.getParameter("password");

        Boolean autenticado = loginService.autenticar(email, senha, req);

        if(autenticado) {
            resp.sendRedirect(req.getContextPath()+"/user");
        }
        else{
            req.getSession().setAttribute("error","Email ou senha incorretos");
            resp.sendRedirect("login.jsp");
        }
    }

}
