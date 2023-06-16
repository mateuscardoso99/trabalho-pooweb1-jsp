package service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import config.PropertiesLoad;
import dao.DocumentoDAO;
import model.Documento;
import model.Usuario;
import utils.BuscarUsuarioLogado;
import utils.FileUtils;
import utils.Validator;

public class DocumentoService {
    private static final String PATH = PropertiesLoad.loadProperties().getProperty("docs_folder");
    private static final String[] EXTENSOES = {
        ".pdf",".txt",".html",".jpe",".jpeg",".jpg",".png",".svg",".webp",".gif",".js",".php",".java",".c",".cpp",".py"
    };
    private DocumentoDAO documentoDAO = new DocumentoDAO();

    public List<Documento> findAll(HttpServletRequest req){
        Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);

        try{
            return documentoDAO.findAll(u.getId());
        }catch(SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean salvar(HttpServletRequest req){
        try{
            Map<String, List<String>> erros = validar(req);

            if(erros.isEmpty()){
                Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);

                Documento documento = new Documento();
                documento.setIdUsuario(u.getId());

                //upload documento
                Part file = req.getPart("arquivo");

                String uploadPath = req.getServletContext().getRealPath("") + File.separator + PATH;

                File folder = new File(uploadPath);

                //cria pasta docs
                if(!folder.exists())
                    folder.mkdir();

                String hashFileName = FileUtils.generateHashFilename();
                String fileName = hashFileName + FileUtils.getSubmittedFileName(file); 

                file.write(uploadPath + File.separator + fileName);

                documento.setArquivo(fileName);

                if(documentoDAO.salvar(documento))
                    return true;
                
                req.getSession().setAttribute("error", "erro ao salvar documento");
                return false;
            }

            req.getSession().setAttribute("validationErrors", erros);
            return false;
        }catch(Exception ex){
            ex.printStackTrace();
            req.getSession().setAttribute("error", "erro ao salvar documento");
            return false;
        }
    }

    public boolean deletar(HttpServletRequest request){
        Map<String, List<String>> erros = new HashMap<>();

        List<String> errosId = validateId(request);

        if(!errosId.isEmpty())
            erros.put("id", errosId);

        if(erros.isEmpty()){
            Long id = Long.valueOf(request.getParameter("idDoc"));

            Usuario u = BuscarUsuarioLogado.getUsuarioLogado(request);
            Optional<Documento> doc = documentoDAO.findById(id, u.getId());

            if(doc.isPresent()){
                String relativePath = request.getServletContext().getRealPath("") + File.separator + PATH + File.separator;
                
                File file = new File(relativePath + doc.get().getArquivo());
                file.delete();

                if(documentoDAO.deletar(id))
                    return true;

                request.getSession().setAttribute("error", "erro ao apagar o documento");
                return false;                
            }

            request.getSession().setAttribute("error", "documento não encontrado");
            return false;
        }

        request.getSession().setAttribute("validationErrors", erros);
        return false;
    }


    public Optional<String> verificarDocumentoPertenceAoUsuario(HttpServletRequest req){
        Usuario usuarioLogado = BuscarUsuarioLogado.getUsuarioLogado(req);
        Collection<String> docsUsuario = usuarioLogado.getDocumentos().stream().map(doc -> doc.getArquivo()).collect(Collectors.toList());
        Optional<String> documento = docsUsuario.stream().filter(f -> f != null).filter(f -> f.equals(req.getParameter("name"))).findFirst();
        return documento;
    }


    private Map<String, List<String>> validar(HttpServletRequest req) throws IOException, ServletException{
        Map<String, List<String>> erros = new HashMap<>();
        String extensao = FileUtils.getFileExtension(FileUtils.getSubmittedFileName(req.getPart("arquivo")));

        if(extensao.equals("") || !Arrays.asList(EXTENSOES).contains(extensao)){
            erros.put("arquivo", Arrays.asList("tipo de arquivo não permitido"));
        }
        
        return erros;
    }

    private List<String> validateId(HttpServletRequest req){
        List<String> erros = new ArrayList<>();

        String id = req.getParameter("idDoc");

        if(Validator.isEmptyOrNull(id)){
            erros.add("informe o id do contato");
        }
        if(!Validator.isNumber(id)){
            erros.add("id inválido");
        }
        return erros;
    }
}
