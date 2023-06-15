package controller.documento;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DocumentoService;
import utils.FileUtils;

@WebServlet("/user/docs/file")
public class BaixarOuApagarDocumentoController extends HttpServlet{

    private DocumentoService documentoService = new DocumentoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        switch (action) {
            case "apagar":
                if(documentoService.deletar(req)){
                    req.getSession().setAttribute("success", "documento excluído com sucesso");
                }
                resp.sendRedirect(req.getContextPath()+"/user/docs/show");
                break;

            case "baixar":
                if(!documentoService.verificarDocumentoPertenceAoUsuario(req).isPresent()){
                    resp.sendRedirect(req.getContextPath()+"/logout");
                    return;
                }
                //ServletContext é o objeto criado pelo Servlet Container para compartilhar parâmetros iniciais ou informações de configuração para todo o aplicativo.
                String relativePath = req.getServletContext().getRealPath("") + File.separator + req.getParameter("name");
                
                //busca mime type do arquivo
                String mimeType = getServletContext().getMimeType(relativePath);

                FileUtils.retornarArquivoResponse(relativePath, mimeType, resp, true);
                break;

            default:
                req.getSession().setAttribute("error", "ação inválida");
                resp.sendRedirect(req.getContextPath()+"/user/docs/show");
                break;
        }

        
    }
    
    
}
