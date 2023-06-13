package controller.contato;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Usuario;
import utils.BuscarUsuarioLogado;

@WebServlet("/fotos_contato/*")
public class ViewFotoController extends HttpServlet{

    //verifica se a foto é realmente do usuário logado
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = BuscarUsuarioLogado.getUsuarioLogado(req);

        Collection<String> fotosUsuario = usuarioLogado.getContatos().stream().map(c -> c.getFoto()).collect(Collectors.toList());
        Optional<String> foto = fotosUsuario.stream().filter(f -> f != null).filter(f -> f.equals(req.getParameter("name"))).findFirst();

        if(!foto.isPresent()){
            resp.sendRedirect(req.getContextPath()+"/logout");
            return;
        }

        //ServletContext é o objeto criado pelo Servlet Container para compartilhar parâmetros iniciais ou informações de configuração para todo o aplicativo.
        String relativePath = req.getServletContext().getRealPath("");

        File file = new File(relativePath+"/fotos_contato/"+req.getParameter("name"));

        //FileInputStream destina-se à leitura de fluxos de bytes brutos, como dados de imagem
        FileInputStream fileInputStream = new FileInputStream(file);
        
        String mimeType = getServletContext().getMimeType(relativePath+"/fotos_contato/"+req.getParameter("name"));

        resp.setContentType(mimeType);
        resp.setContentLength((int)file.length());

        OutputStream outputStream = resp.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        //método read() da classe InputStream lê um byte de dados do fluxo de entrada e coloca dentro do buffer. O próximo byte de dados é retornado ou -1 se o final do arquivo for atingido
        while((bytesRead = fileInputStream.read(buffer)) != -1){
            outputStream.write(buffer,0,bytesRead);
        }

        fileInputStream.close();
        outputStream.close();
    }
    
}
