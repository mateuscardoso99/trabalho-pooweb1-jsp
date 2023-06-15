package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import config.PropertiesLoad;
import dao.UsuarioDAO;
import model.Usuario;
import utils.BuscarUsuarioLogado;
import utils.Validator;

public class UsuarioService {
    private static final String PATH_FOTOS = PropertiesLoad.loadProperties().getProperty("fotos_folder");
    private static final String PATH_DOCS = PropertiesLoad.loadProperties().getProperty("docs_folder");
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean autenticar(Usuario u, HttpServletRequest req){
        Map<String,List<String>> errors = validar(u,false,true);

        if (errors.isEmpty()) {
            Optional<Usuario> findUsuario = usuarioDAO.findByEmail(u.getEmail());
            HttpSession session = req.getSession();

            if(findUsuario.isPresent()){
                if(new BCryptPasswordEncoder().matches(u.getSenha(), findUsuario.get().getSenha())){
                    Usuario usuario = findUsuario.get();
                    usuarioDAO.getDadosUsuario(usuario);
                    session.setAttribute("usuario", usuario);
                    session.setAttribute("logado","true");
                    return true;
                }
            }

            req.setAttribute("error","Email ou senha incorretos");
            return false;
        }
        req.setAttribute("validationErrors", errors);
        return false;        
    }

    public boolean cadastrar(Usuario usuario, HttpServletRequest req){
        Map<String,List<String>> errors = validar(usuario,true,true);

        if (errors.isEmpty()) {
            Optional<Usuario> verificaEmail = usuarioDAO.findByEmail(usuario.getEmail());

            if(!verificaEmail.isPresent()){
                usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
    
                if(usuarioDAO.salvar(usuario))
                    return true;
            }

            req.getSession().setAttribute("error","erro ao criar a conta");
            return false;            
        }
        
        HttpSession session = req.getSession();
        session.setAttribute("validationErrors", errors);
        return false;       
    }

    public boolean atualizar(HttpServletRequest req, Usuario usuarioUpdate){
        try{
            //se usuario enviou a senha na request então ele quer alterar a senha
            Boolean verificarSenha = (usuarioUpdate.getSenha() != "") ? true : false;

            Map<String,List<String>> errors = validar(usuarioUpdate,true, verificarSenha);

            if (errors.isEmpty()) {
                Usuario usuarioLogado = BuscarUsuarioLogado.getUsuarioLogado(req);
                usuarioUpdate.setId(usuarioLogado.getId());
                usuarioUpdate.setContatos(usuarioLogado.getContatos());
                usuarioUpdate.setLinks(usuarioLogado.getLinks());

                if(!Validator.isEmptyOrNull(usuarioUpdate.getSenha()))
                    usuarioUpdate.setSenha(new BCryptPasswordEncoder().encode(usuarioUpdate.getSenha()));

                //se o usuario tem documentos salvos.. e ele trocou o nome, a pasta tem o nome antigo dele, vai ser preciso criar uma nova pasta com o novo nome
                //e mover os arquivos dele pra essa nova pasta, atualizar o caminho dos docs dele com o nome da nova pasta e apagar a pasta antiga
                String existePastaDocs = req.getServletContext().getRealPath("") + File.separator + "docs" + File.separator + usuarioLogado.getNome().replace(" ", "-") + "_" + usuarioLogado.getId();
                
                if(!usuarioLogado.getNome().equals(usuarioUpdate.getNome()) && Files.exists(Paths.get(existePastaDocs))){
                    String newPath = req.getServletContext().getRealPath("") + File.separator + "docs" + File.separator + usuarioUpdate.getNome().replace(" ", "-") + "_" + usuarioLogado.getId();

                    File novaPasta = new File(newPath);
                    novaPasta.mkdir();

                    //move da pasta antiga pra nova
                    Files.move(Paths.get(existePastaDocs), Paths.get(newPath), StandardCopyOption.REPLACE_EXISTING);

                    File deletarPasta = new File(existePastaDocs);
                    deletarPasta.delete();

                    String nomePastaAntiga = usuarioLogado.getNome().replace(" ", "-") + "_" + usuarioLogado.getId();
                    String nomeNovaPasta = usuarioUpdate.getNome().replace(" ", "-") + "_" + usuarioLogado.getId();

                    //atualiza no db os caminhos dos arquivos
                    usuarioLogado.getDocumentos().forEach(doc -> {
                        String updatePath = doc.getArquivo().replace(nomePastaAntiga,nomeNovaPasta);
                        usuarioDAO.atualizarCaminhoDocs(updatePath, usuarioLogado.getId(), doc.getId());
                    });
                }

                if(usuarioDAO.atualizar(usuarioUpdate)){
                    req.getSession().setAttribute("usuario", usuarioUpdate);//atualiza dados do usuario na sessão
                    return true;
                }
                
                req.getSession().setAttribute("error","erro ao atualizar perfil");
                return false;
            }
            
            req.getSession().setAttribute("validationErrors", errors);
            return false;
        }catch(IOException e){
            e.printStackTrace();
            req.getSession().setAttribute("error","erro ao atualizar perfil");
            return false;
        }
    }

    public boolean apagarConta(HttpServletRequest req){
        Usuario usuarioLogado = BuscarUsuarioLogado.getUsuarioLogado(req);
        try{
            usuarioDAO.getDadosUsuario(usuarioLogado);

            String relativePath = req.getServletContext().getRealPath("");

            usuarioLogado.getContatos().stream().forEach(c -> {
                File f = new File(relativePath + File.separator + PATH_FOTOS + File.separator + c.getFoto());
                f.delete();
            });

            usuarioLogado.getDocumentos().stream().forEach(doc -> {
                File f = new File(relativePath + File.separator + doc.getArquivo());
                f.delete();
            });

            //apagar pasta dos docs do usuario
            File f = new File(
                relativePath + 
                File.separator + 
                PATH_DOCS + 
                File.separator + 
                usuarioLogado.getNome().replace(" ", "-") + 
                "_" +
                usuarioLogado.getId());
            f.delete();

            return usuarioDAO.deletar(usuarioLogado);
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    private Map<String,List<String>> validar(Usuario u, boolean verificarNome, boolean verificarSenha){
        Map<String,List<String>> errors = new HashMap<>();

        if(verificarNome){
            if(Validator.isEmptyOrNull(u.getNome())){
                errors.put("nome", Arrays.asList("Nome inválido"));
            }
        }
        if(Validator.isEmptyOrNull(u.getEmail()) || !Validator.isEmail(u.getEmail())){
            errors.put("email", Arrays.asList("E-mail inválido"));
        }
        if(verificarSenha){
            if(Validator.isEmptyOrNull(u.getSenha()) || !Validator.isValidPassword(u.getSenha())){
                errors.put("senha", Arrays.asList("Senha inválida"));
            }
        }
        return errors;
    }
}
