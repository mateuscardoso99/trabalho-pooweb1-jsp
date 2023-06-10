package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import dao.LinkDAO;
import model.Link;
import model.Usuario;
import utils.BuscarUsuarioLogado;
import utils.Validator;

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
        Map<String, List<String>> erros = validar(link);

        if(erros.isEmpty()){
            Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);
            link.setIdUsuario(u.getId());

            if(linkDAO.salvar(link))
                return true;
            
            req.setAttribute("error", "erro ao salvar link");
            return false;
        }

        req.setAttribute("validationErrors", erros);
        return false;        
    }

    public boolean atualizar(HttpServletRequest req, Link l, String idLink){
        Map<String, List<String>> erros = validar(l);

        List<String> errosId = validateId(idLink);

        if(!errosId.isEmpty())
            erros.put("id", errosId);

        if(erros.isEmpty()){
            Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);
            Optional<Link> findLink = linkDAO.findById(l.getId(), u.getId());
            if(findLink.isPresent()){
                if(linkDAO.atualizar(l))
                    return true;

                req.getSession().setAttribute("error", "erro ao salvar link");
                return false;
            }
        }
        req.getSession().setAttribute("validationErrors", erros);
        return false;
    }

    public boolean deletar(HttpServletRequest request, String idLink){
        Map<String, List<String>> erros = new HashMap<>();

        List<String> errosId = validateId(idLink);

        if(!errosId.isEmpty())
            erros.put("id", errosId);

        if(erros.isEmpty()){  
            Usuario u = BuscarUsuarioLogado.getUsuarioLogado(request);
            Optional<Link> link = linkDAO.findById(Long.valueOf(idLink), u.getId());
            if(link.isPresent()){
                if(linkDAO.deletar(Long.valueOf(idLink)))
                    return true;

                request.getSession().setAttribute("error", "erro ao apagar o link");
                return false;                
            }
            return false;
        }

        request.getSession().setAttribute("validationErrors", erros);
        return false;
    }

    private Map<String, List<String>> validar(Link l){
        Map<String, List<String>> erros = new HashMap<>();

        if(Validator.isEmptyOrNull(l.getDescricao())){
            erros.put("descricao",Arrays.asList("descrição inválida"));
        }
        if(Validator.isEmptyOrNull(l.getUrl())){
            erros.put("url",Arrays.asList("URL inválida"));
        }
        
        return erros;
    }

    private List<String> validateId(String id){
        List<String> erros = new ArrayList<>();

        if(Validator.isEmptyOrNull(id)){
            erros.add("link inválido");
        }
        if(!Validator.isNumber(id)){
            erros.add("id inválido");
        }
        return erros;
    }
}
