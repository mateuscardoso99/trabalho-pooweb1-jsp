package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Tudo em WEB-INF não é acessível pelo navegador.
 * É uma boa prática colocá-los exatamente porque você nunca deseja que alguém acesse um JSP diretamente do navegador. 
 * JSPs são visualizações e as solicitações devem passar primeiro por um controlador, que então despacha 
 * (isto é, encaminha , não redireciona )
 */

@WebServlet("/user")
public class HomeController extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/WEB-INF/principal.jsp").forward(req, resp);
    }

}
