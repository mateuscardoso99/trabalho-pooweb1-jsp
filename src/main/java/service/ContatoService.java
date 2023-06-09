package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import dao.ContatoDAO;
import model.Contato;
import model.Usuario;
import utils.BuscarUsuarioLogado;

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

    public boolean salvar(HttpServletRequest req, Contato contato){
        Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);
        contato.setIdUsuario(u.getId());
        return contatoDAO.salvar(contato);
    }

    public boolean atualizar(HttpServletRequest req, Contato contato){
        Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);
        Optional<Contato> findContato = contatoDAO.findById(contato.getId(), u.getId());
        if(findContato.isPresent()){
            return contatoDAO.atualizar(contato);
        }
        return false;
    }

    public boolean deletar(HttpServletRequest request, Long idContato){
        Usuario u = BuscarUsuarioLogado.getUsuarioLogado(request);
        Optional<Contato> contato = contatoDAO.findById(idContato, u.getId());
        if(contato.isPresent()){
            return contatoDAO.deletar(idContato);
        }
        return false;
    }
}
