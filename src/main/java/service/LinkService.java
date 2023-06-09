package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import dao.LinkDAO;
import model.Link;
import model.Usuario;
import utils.BuscarUsuarioLogado;

public class LinkService {
    private LinkDAO linkDAO = new LinkDAO();

    public List<Link> findAll(HttpServletRequest req){
        Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);

        try{
            return linkDAO.findAll(u.getId());
        }catch(SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean salvar(HttpServletRequest req, Link link){
        Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);
        link.setIdUsuario(u.getId());
        return linkDAO.salvar(link);
    }

    public boolean atualizar(HttpServletRequest req, Link l){
        Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);
        Optional<Link> findLink = linkDAO.findById(l.getId(), u.getId());
        if(findLink.isPresent()){
            return linkDAO.atualizar(l);
        }
        return false;
    }

    public boolean deletar(HttpServletRequest request, Long idLink){
        Usuario u = BuscarUsuarioLogado.getUsuarioLogado(request);
        Optional<Link> link = linkDAO.findById(idLink, u.getId());
        if(link.isPresent()){
            return linkDAO.deletar(idLink);
        }
        return false;
    }
}
