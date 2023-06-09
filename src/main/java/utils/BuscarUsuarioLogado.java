package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Usuario;

public class BuscarUsuarioLogado {
    public static Usuario getUsuarioLogado(HttpServletRequest request){
        HttpSession session = request.getSession();
        Usuario u = (Usuario) session.getAttribute("usuario");
        return u;
    }
}
