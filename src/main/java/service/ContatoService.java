package service;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import config.PropertiesLoad;
import dao.ContatoDAO;
import model.Contato;
import model.Usuario;
import utils.BuscarUsuarioLogado;
import utils.Validator;

public class ContatoService {
    private ContatoDAO contatoDAO = new ContatoDAO();

    public List<Contato> findAll(HttpServletRequest req){
        Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);

        try{
            return contatoDAO.findAll(u.getId());
        }catch(SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean salvar(HttpServletRequest req){
        try{
            Map<String, List<String>> erros = validar(req);

            if(erros.isEmpty()){
                String nome = req.getParameter("nome");
                String telefone = req.getParameter("telefone");

                Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);

                Contato contato = new Contato(nome, telefone);
                contato.setIdUsuario(u.getId());

                //upload foto
                if(req.getPart("foto").getSize() > 0){
                    Part file = req.getPart("foto");

                    String uploadPath = req.getServletContext().getRealPath("") + File.separator + PropertiesLoad.loadProperties().getProperty("fotos_folder");
                    File uploadDir = new File(uploadPath);

                    String hashFileName = generateHashFilename();
                    String fileName = hashFileName + getSubmittedFileName(file); 

                    //cria diretorio
                    if(!uploadDir.exists())
                        uploadDir.mkdir();

                    file.write(uploadPath + File.separator + fileName);

                    contato.setFoto(fileName);
                }

                if(contatoDAO.salvar(contato))
                    return true;
                
                req.setAttribute("error", "erro ao salvar contato");
                return false;
            }

            req.setAttribute("validationErrors", erros);
            return false;
        }catch(Exception ex){
            ex.printStackTrace();
            req.setAttribute("error", "erro ao salvar contato");
            return false;
        }
    }

    public boolean atualizar(HttpServletRequest req){
        Map<String, List<String>> erros = validar(req);

        List<String> errosId = validateId(req);

        if(!errosId.isEmpty())
            erros.put("id", errosId);

        if(erros.isEmpty()){
            Long id = Long.parseLong(req.getParameter("idContato"));
            String nome = req.getParameter("nome");
            String telefone = req.getParameter("telefone");

            Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);
            Contato c = new Contato(id, nome, telefone);

            Optional<Contato> findContato = contatoDAO.findById(c.getId(), u.getId());

            if(findContato.isPresent()){
                if(contatoDAO.atualizar(c))
                    return true;

                req.getSession().setAttribute("error", "erro ao salvar contato");
                return false;
            }
        }
        req.getSession().setAttribute("validationErrors", erros);
        return false;
    }

    public boolean deletar(HttpServletRequest request){
        Map<String, List<String>> erros = new HashMap<>();

        List<String> errosId = validateId(request);

        if(!errosId.isEmpty())
            erros.put("id", errosId);

        if(erros.isEmpty()){
            Long id = Long.valueOf(request.getParameter("idContato"));

            Usuario u = BuscarUsuarioLogado.getUsuarioLogado(request);
            Optional<Contato> c = contatoDAO.findById(id, u.getId());

            if(c.isPresent()){
                String relativePath = request.getServletContext().getRealPath("");
                
                File file = new File(relativePath+"/fotos_contato/"+c.get().getFoto());
                file.delete();

                if(contatoDAO.deletar(Long.valueOf(id)))
                    return true;

                request.getSession().setAttribute("error", "erro ao apagar o contato");
                return false;                
            }
            return false;
        }

        request.getSession().setAttribute("validationErrors", erros);
        return false;
    }

    private Map<String, List<String>> validar(HttpServletRequest req){
        Map<String, List<String>> erros = new HashMap<>();

        String nome = req.getParameter("nome");
        String telefone = req.getParameter("telefone");

        if(Validator.isEmptyOrNull(nome)){
            erros.put("nome",Arrays.asList("nome inválido"));
        }
        if(Validator.isEmptyOrNull(telefone)){
            erros.put("telefone",Arrays.asList("telefone inválido"));
        }
        
        return erros;
    }

    private List<String> validateId(HttpServletRequest req){
        List<String> erros = new ArrayList<>();

        String id = req.getParameter("idContato");

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
                .ints(12, 0, chrs.length()) // 12 is the length of the string you want
                .mapToObj(i -> chrs.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        }catch(NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return customTag;
    }
}
