package controller;

import service.RegisterService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Usuario;

import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private RegisterService registerService = new RegisterService();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String senha = req.getParameter("password");

        Usuario u = new Usuario(nome, email, senha);

        if(registerService.cadastrar(u, req)){
            req.getSession().setAttribute("success","conta criada com sucesso");
            resp.sendRedirect("login.jsp");
        }
        else{
            req.getSession().setAttribute("error","erro ao criar a conta");
            resp.sendRedirect("register.jsp");
        }    
    }
}
