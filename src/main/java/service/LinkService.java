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

    public boolean salvar(HttpServletRequest req){
        Map<String, List<String>> erros = validar(req);

        if(erros.isEmpty()){
            String url = req.getParameter("url");
            String descricao = req.getParameter("descricao");

            Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);

            Link link = new Link(url, descricao);
            link.setIdUsuario(u.getId());

            if(linkDAO.salvar(link))
                return true;
            
            req.setAttribute("error", "erro ao salvar link");
            return false;
        }

        req.setAttribute("validationErrors", erros);
        return false;
    }

    public boolean atualizar(HttpServletRequest req){
        Map<String, List<String>> erros = validar(req);

        List<String> errosId = validateId(req);

        if(!errosId.isEmpty())
            erros.put("id", errosId);

        if(erros.isEmpty()){
            Long id = Long.parseLong(req.getParameter("idLink"));
            String url = req.getParameter("url");
            String descricao = req.getParameter("descricao");

            Usuario u = BuscarUsuarioLogado.getUsuarioLogado(req);
            Link link = new Link(id, url, descricao);

            Optional<Link> findLink = linkDAO.findById(link.getId(), u.getId());

            if(findLink.isPresent()){
                if(linkDAO.atualizar(link))
                    return true;

                req.getSession().setAttribute("error", "erro ao salvar link");
                return false;
            }

            req.getSession().setAttribute("error", "link não encontrado");
            return false;
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
            Long id = Long.valueOf(request.getParameter("idLink"));

            Usuario u = BuscarUsuarioLogado.getUsuarioLogado(request);
            Optional<Link> link = linkDAO.findById(id, u.getId());

            if(link.isPresent()){
                if(linkDAO.deletar(Long.valueOf(id)))
                    return true;

                request.getSession().setAttribute("error", "erro ao apagar o link");
                return false;                
            }
            return false;
        }

        request.getSession().setAttribute("validationErrors", erros);
        return false;
    }

    private Map<String, List<String>> validar(HttpServletRequest req){
        Map<String, List<String>> erros = new HashMap<>();

        String url = req.getParameter("url");
        String descricao = req.getParameter("descricao");

        if(Validator.isEmptyOrNull(descricao)){
            erros.put("descricao",Arrays.asList("descrição inválida"));
        }
        if(Validator.isEmptyOrNull(url)){
            erros.put("url",Arrays.asList("URL inválida"));
        }
        
        return erros;
    }

    private List<String> validateId(HttpServletRequest req){
        List<String> erros = new ArrayList<>();

        String id = req.getParameter("idLink");

        if(Validator.isEmptyOrNull(id)){
            erros.add("informe o id do link");
        }
        if(!Validator.isNumber(id)){
            erros.add("id inválido");
        }
        return erros;
    }
}
