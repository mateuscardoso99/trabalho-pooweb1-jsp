package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Link;

public class LinkDAO {
    private StringBuilder sql;

    public List<Link> findAll(Long idUsuario) throws SQLException{
        List<Link> links = new ArrayList<>();
        sql = new StringBuilder();
        sql.append("SELECT l.*, u.id as id_usuario FROM link l, usuario u WHERE l.id_usuario = u.id AND u.id = ?;");

        PreparedStatement preparedStatement = ConectaDB.getConnection().prepareStatement(sql.toString());
        preparedStatement.setLong(1, idUsuario);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Link l = new Link();
            l.setId(resultSet.getLong("id"));
            l.setUrl(resultSet.getString("url"));
            l.setDescricao(resultSet.getString("descricao"));
            l.setIdUsuario(resultSet.getLong("id_usuario"));
            links.add(l);
        }
        preparedStatement.close();
        return links;
    }

    public Optional<Link> findById(Long id, Long idUsuario){
        Link link = null;

        try{
            sql = new StringBuilder();
            sql.append("SELECT * FROM link WHERE id = ? AND id_usuario = ?;");

            PreparedStatement preparedStatement = ConectaDB.getConnection().prepareStatement(sql.toString());
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                link = new Link();
                link.setId(resultSet.getLong("id"));
                link.setUrl(resultSet.getString("url"));
                link.setDescricao(resultSet.getString("descricao"));
            }

            preparedStatement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return Optional.of(link);
    }

    public boolean salvar(Link link){
        try(Connection connection = new ConectaDB().getConnection2()){
            sql = new StringBuilder();
            sql.append("INSERT INTO link(url,descricao,id_usuario) VALUES(?,?,?);");

            PreparedStatement ps = connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, link.getUrl());
            ps.setString(2, link.getDescricao());
            ps.setLong(3, link.getIdUsuario());
            int result = ps.executeUpdate();
            ps.close();

            if (result == 0)
                return Boolean.FALSE;

            return Boolean.TRUE;
        }catch(SQLException ex) {
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public boolean deletar(Long idLink){
        try{
            sql = new StringBuilder();
            sql.append("DELETE FROM link WHERE id = ?;");

            PreparedStatement ps = ConectaDB.getConnection().prepareStatement(sql.toString());
            ps.setLong(1, idLink);
            int result = ps.executeUpdate();
            ps.close();

            if (result == 0)
                return Boolean.FALSE;

            return Boolean.TRUE;
        }catch(SQLException e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    public boolean atualizar(Link link){
        try{
            sql = new StringBuilder();
            sql.append("UPDATE link SET url = ?, descricao = ? WHERE id = ?;");

            PreparedStatement ps = ConectaDB.getConnection().prepareStatement(sql.toString());
            ps.setString(1, link.getUrl());
            ps.setString(2, link.getDescricao());
            ps.setLong(3, link.getId());
            int result = ps.executeUpdate();
            ps.close();

            if (result == 0)
                return Boolean.FALSE;

            return Boolean.TRUE;
        }catch(SQLException e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
