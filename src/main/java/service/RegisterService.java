package service;

import dao.UsuarioDAO;
import model.Usuario;
import utils.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class RegisterService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean cadastrar(Usuario usuario, HttpServletRequest req){
        List<String> errors = new ArrayList<>();

        if(Validator.isEmptyOrNull(usuario.getNome())){
            errors.add("Nome inválido");
        }
        if(Validator.isEmptyOrNull(usuario.getEmail()) || !Validator.isEmail(usuario.getEmail())){
            errors.add("E-mail inválido");
        }
        if(Validator.isEmptyOrNull(usuario.getSenha()) || !Validator.isValidPassword(usuario.getSenha())){
            errors.add("Senha inválida");
        }

        if (errors.isEmpty()) {
            usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
            return usuarioDAO.salvar(usuario);
        }
        else{
            HttpSession session = req.getSession();
            session.setAttribute("validationErrors", errors);
            return false;
        }        
    }
}
