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
import dao.ContatoDAO;
import model.Contato;
import model.Usuario;
import utils.BuscarUsuarioLogado;
import utils.FileUtils;
import utils.Validator;

public class ContatoService {
    private static final String PATH = PropertiesLoad.loadProperties().getProperty("fotos_folder");
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
            boolean validarArquivo = (req.getPart("foto").getSize() > 0) ? true : false;
            Map<String, List<String>> erros = validar(req, validarArquivo);

            if(erros.isEmpty()){
                String nome = req.getParameter("nome");
                String telefone = req.getParameter("telefone");

                Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);

                Contato contato = new Contato(nome, telefone);
                contato.setIdUsuario(u.getId());

                //upload foto
                if(req.getPart("foto").getSize() > 0){
                    Part file = req.getPart("foto");

                    String uploadPath = req.getServletContext().getRealPath("") + File.separator + PATH;
                    File uploadDir = new File(uploadPath);

                    String hashFileName = FileUtils.generateHashFilename();
                    String fileName = hashFileName + FileUtils.getSubmittedFileName(file); 

                    //cria diretorio se não existe
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
        try{
            Map<String, List<String>> erros = validar(req,false);

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
        }catch(Exception e){
            e.printStackTrace();
            req.getSession().setAttribute("error", "erro ao salvar contato");
            return false;
        }
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
                
                File file = new File(relativePath + File.separator + PATH + File.separator + c.get().getFoto());
                file.delete();

                if(contatoDAO.deletar(Long.valueOf(id)))
                    return true;

                request.getSession().setAttribute("error", "erro ao apagar o contato");
                return false;                
            }

            request.getSession().setAttribute("error", "contato não encontrado");
            return false;
        }

        request.getSession().setAttribute("validationErrors", erros);
        return false;
    }


    public Optional<String> verificarFotoPertenceAoUsuario(HttpServletRequest req){
        Usuario usuarioLogado = BuscarUsuarioLogado.getUsuarioLogado(req);
        Collection<String> fotosUsuario = usuarioLogado.getContatos().stream().map(c -> c.getFoto()).collect(Collectors.toList());
        Optional<String> foto = fotosUsuario.stream().filter(f -> f != null).filter(f -> f.equals(req.getParameter("name"))).findFirst();
        return foto;
    }


    private Map<String, List<String>> validar(HttpServletRequest req, boolean validarArquivo) throws IOException, ServletException{
        Map<String, List<String>> erros = new HashMap<>();

        String nome = req.getParameter("nome");
        String telefone = req.getParameter("telefone");

        if(Validator.isEmptyOrNull(nome)){
            erros.put("nome",Arrays.asList("nome inválido"));
        }
        if(Validator.isEmptyOrNull(telefone)){
            erros.put("telefone",Arrays.asList("telefone inválido"));
        }
        if(validarArquivo){
            String extensao = Validator.getFileExtension(FileUtils.getSubmittedFileName(req.getPart("foto")));

            if(extensao == "" || (!extensao.equals(".png") && !extensao.equals(".jpg") && !extensao.equals(".jpeg"))){
                erros.put("foto", Arrays.asList("arquivo inválido"));
            }
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
}
