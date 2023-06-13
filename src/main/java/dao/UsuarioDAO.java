package dao;

import model.Contato;
import model.Link;
import model.Usuario;
import utils.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAO {
    private StringBuilder sql;

    public Optional<Usuario> findById(Long id) throws SQLException{
        Usuario usuario = null;

        sql = new StringBuilder();
        sql.append("SELECT * FROM usuario WHERE id = ?;");

        PreparedStatement preparedStatement = ConectaDB.getConnection().prepareStatement(sql.toString());
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            usuario = new Usuario();
            usuario.setId(resultSet.getLong("id"));
            usuario.setNome(resultSet.getString("nome"));
            usuario.setEmail(resultSet.getString("email"));
            usuario.setSenha(resultSet.getString("senha"));
        }

        preparedStatement.close();

        return Optional.of(usuario);
    }

    public Optional<Usuario> findByEmail(String email){
        try(Connection conn = ConectaDB.getConnection()){
            sql = new StringBuilder();
            sql.append("SELECT * FROM usuario WHERE email = ?;");

            PreparedStatement preparedStatement = conn.prepareStatement(sql.toString());
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();

            Usuario usuario = null;

            if(resultSet.next()){
                usuario = new Usuario();
                usuario.setId(resultSet.getLong("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setSenha(resultSet.getString("senha"));
            }

            preparedStatement.close();
            return Optional.ofNullable(usuario);

        }catch (SQLException ex){
            ex.printStackTrace();
            return Optional.ofNullable(null);
        }

    }

    public boolean salvar(Usuario usuario){
        try(Connection connection = new ConectaDB().getConnection2()){
            sql = new StringBuilder();
            sql.append("INSERT INTO usuario(nome,email,senha) VALUES(?,?,?);");

            PreparedStatement ps = connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
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

    public boolean deletar(Usuario usuario) throws SQLException{
        sql = new StringBuilder();
        sql.append("DELETE FROM usuario WHERE id = ?;");

        PreparedStatement ps = ConectaDB.getConnection().prepareStatement(sql.toString());
        ps.setLong(1, usuario.getId());
        int result = ps.executeUpdate();
        ps.close();

        if (result == 0)
            return Boolean.FALSE;

        return Boolean.TRUE;
    }

    public boolean atualizar(Usuario usuario){
        sql = new StringBuilder();
        sql.append("UPDATE usuario SET nome = ?, email = ?");

        if(Validator.isEmptyOrNull(usuario.getSenha()))
            sql.append(" WHERE id = ?;");
        else
            sql.append(", senha = ? WHERE id = ?;");

        try{
            PreparedStatement ps = ConectaDB.getConnection().prepareStatement(sql.toString());
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());

            if(Validator.isEmptyOrNull(usuario.getSenha())){
                ps.setLong(3, usuario.getId());
            }
            else{
                ps.setString(3, usuario.getSenha());
                ps.setLong(4, usuario.getId());
            }

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

    public Usuario getDadosUsuario(Usuario u){
        sql = new StringBuilder();
        sql.append("SELECT c.id AS cid, c.*, l.id AS lid, l.* FROM contato c JOIN usuario u on c.id_usuario = u.id JOIN link l on u.id = l.id_usuario WHERE u.id = ?");

        try(Connection conn = new ConectaDB().getConnection2()){

            List<Contato> contatosUsuario = new ArrayList<>();
            List<Link> linksUsuario = new ArrayList<>();

            PreparedStatement ps = ConectaDB.getConnection().prepareStatement(sql.toString());
            ps.setLong(1, u.getId());
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                Contato c = new Contato(
                    resultSet.getLong("cid"), 
                    resultSet.getString("telefone"),
                    resultSet.getString("nome"), 
                    resultSet.getString("foto"),
                    resultSet.getLong("id_usuario")
                );
                Link l = new Link(
                    resultSet.getLong("lid"), 
                    resultSet.getString("url"),
                    resultSet.getString("descricao"),
                    resultSet.getLong("id_usuario")
                );
                contatosUsuario.add(c);
                linksUsuario.add(l);
            }

            u.setContatos(contatosUsuario);
            u.setLinks(linksUsuario);
            
        }catch(SQLException e){
            e.printStackTrace();
        }

        return u;
    }
}
