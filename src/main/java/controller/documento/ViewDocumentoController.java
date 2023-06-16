package controller.documento;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.PropertiesLoad;
import service.DocumentoService;
import utils.FileUtils;

@WebServlet("/docs/*")
public class ViewDocumentoController extends HttpServlet{
    private static final String PATH = PropertiesLoad.loadProperties().getProperty("docs_folder");
    private DocumentoService documentoService = new DocumentoService();

    //verifica se a foto é realmente do usuário logado
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!documentoService.verificarDocumentoPertenceAoUsuario(req).isPresent()){
            resp.sendRedirect(req.getContextPath()+"/logout");
            return;
        }

        //ServletContext é o objeto criado pelo Servlet Container para compartilhar parâmetros iniciais ou informações de configuração para todo o aplicativo.
        String relativePath = req.getServletContext().getRealPath("") + File.separator + PATH + File.separator + req.getParameter("name");
        
        //busca mime type do arquivo
        String mimeType = getServletContext().getMimeType(relativePath);

        FileUtils.retornarArquivoResponse(relativePath, mimeType, resp, false);
    }
    
}
