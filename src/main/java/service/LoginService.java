package service;

import dao.UsuarioDAO;
import model.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class LoginService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean autenticar(String email, String senha, HttpServletRequest req){
        Optional<Usuario> usuario = usuarioDAO.findByEmail(email);
        HttpSession session = req.getSession();

        if(usuario.isPresent()){
            if(new BCryptPasswordEncoder().matches(senha, usuario.get().getSenha())){
                session.setAttribute("usuario", usuario.get());
                session.setAttribute("logado","true");
                return true;
            }
            return false;
        }

        return false;
    }
}
