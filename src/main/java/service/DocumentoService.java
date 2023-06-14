package service;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import config.PropertiesLoad;
import dao.DocumentoDAO;
import model.Documento;
import model.Usuario;
import utils.BuscarUsuarioLogado;
import utils.Validator;

public class DocumentoService {
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

                //upload foto
                Part file = req.getPart("arquivo");

                String uploadPath = req.getServletContext().getRealPath("") + File.separator + PropertiesLoad.loadProperties().getProperty("docs_folder");
                
                File folder = new File(uploadPath);

                //cria pasta docs
                if(!folder.exists())
                    folder.mkdir();

                uploadPath += File.separator + u.getNome() + "_" + u.getId();

                File uploadDir = new File(uploadPath);

                //cria pasta do usuário dentro da pasta docs
                if(!uploadDir.exists())
                    uploadDir.mkdir();

                String hashFileName = generateHashFilename();
                String fileName = hashFileName + getSubmittedFileName(file); 

                file.write(uploadPath + File.separator + fileName);

                documento.setArquivo("/docs/"+u.getNome()+"_"+u.getId()+"/"+fileName);

                if(documentoDAO.salvar(documento))
                    return true;
                
                req.setAttribute("error", "erro ao salvar documento");
                return false;
            }

            req.setAttribute("validationErrors", erros);
            return false;
        }catch(Exception ex){
            ex.printStackTrace();
            req.setAttribute("error", "erro ao salvar documento");
            return false;
        }
    }

    // public boolean atualizar(HttpServletRequest req){
    //     try{
    //         Map<String, List<String>> erros = validar(req,false);

    //         List<String> errosId = validateId(req);

    //         if(!errosId.isEmpty())
    //             erros.put("id", errosId);

    //         if(erros.isEmpty()){
    //             Long id = Long.parseLong(req.getParameter("idContato"));
    //             String nome = req.getParameter("nome");
    //             String telefone = req.getParameter("telefone");

    //             Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);
    //             Contato c = new Contato(id, nome, telefone);

    //             Optional<Contato> findContato = contatoDAO.findById(c.getId(), u.getId());

    //             if(findContato.isPresent()){
    //                 if(contatoDAO.atualizar(c))
    //                     return true;

    //                 req.getSession().setAttribute("error", "erro ao salvar contato");
    //                 return false;
    //             }
    //         }
    //         req.getSession().setAttribute("validationErrors", erros);
    //         return false;
    //     }catch(Exception e){
    //         e.printStackTrace();
    //         req.getSession().setAttribute("error", "erro ao salvar contato");
    //         return false;
    //     }
    // }

    // public boolean deletar(HttpServletRequest request){
    //     Map<String, List<String>> erros = new HashMap<>();

    //     List<String> errosId = validateId(request);

    //     if(!errosId.isEmpty())
    //         erros.put("id", errosId);

    //     if(erros.isEmpty()){
    //         Long id = Long.valueOf(request.getParameter("idContato"));

    //         Usuario u = BuscarUsuarioLogado.getUsuarioLogado(request);
    //         Optional<Contato> c = contatoDAO.findById(id, u.getId());

    //         if(c.isPresent()){
    //             String relativePath = request.getServletContext().getRealPath("");
                
    //             File file = new File(relativePath+"/fotos_contato/"+c.get().getFoto());
    //             file.delete();

    //             if(contatoDAO.deletar(Long.valueOf(id)))
    //                 return true;

    //             request.getSession().setAttribute("error", "erro ao apagar o contato");
    //             return false;                
    //         }
    //         return false;
    //     }

    //     request.getSession().setAttribute("validationErrors", erros);
    //     return false;
    // }

    private Map<String, List<String>> validar(HttpServletRequest req) throws IOException, ServletException{
        Map<String, List<String>> erros = new HashMap<>();
        String extensao = Validator.getFileExtension(getSubmittedFileName(req.getPart("arquivo")));

        if(extensao == "" || 
            (!extensao.equals(".png") 
                && !extensao.equals(".jpg") 
                && !extensao.equals(".jpeg")
                && !extensao.equals(".pdf")
                && !extensao.equals(".txt")
                && !extensao.equals(".doc")
                && !extensao.equals(".docx")
            )){
            erros.put("arquivo", Arrays.asList("arquivo inválido"));
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

    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    private String generateHashFilename(){
        String customTag = String.valueOf(new Random().nextInt(15));
        try{
            String chrs = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            customTag = secureRandom
                .ints(20, 0, chrs.length()) // 20 is the length of the string you want
                .mapToObj(i -> chrs.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        }catch(NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return customTag;
    }
}
