package controller.contato;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.PropertiesLoad;
import service.ContatoService;
import utils.FileUtils;

@WebServlet("/fotos_contato/*")
public class ViewFotoController extends HttpServlet{
    
    private ContatoService contatoService = new ContatoService();
    private static final String PATH = PropertiesLoad.loadProperties().getProperty("fotos_folder");

    //verifica se a foto é realmente do usuário logado
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!contatoService.verificarFotoPertenceAoUsuario(req).isPresent()){
            resp.sendRedirect(req.getContextPath()+"/logout");
            return;
        }

        //ServletContext é o objeto criado pelo Servlet Container para compartilhar parâmetros iniciais ou informações de configuração para todo o aplicativo.
        String relativePath = req.getServletContext().getRealPath("") + File.separator + PATH + File.separator + req.getParameter("name");
        
        //busca mime type do arquivo
        String mimeType = getServletContext().getMimeType(relativePath + File.separator + PATH + File.separator + req.getParameter("name"));

        FileUtils.retornarArquivoResponse(relativePath, mimeType, resp, false);
    }
    
}
