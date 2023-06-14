package controller.documento;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DocumentoService;

@WebServlet("/user/docs/salvar")
@MultipartConfig(
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 5 * 5
)
public class SalvarDocumentoController extends HttpServlet{

    private DocumentoService documentoService = new DocumentoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(documentoService.salvar(req)){
            req.getSession().setAttribute("success","documento criado com sucesso");
        }
        resp.sendRedirect(req.getContextPath()+"/user/docs/show");
    }
    
}
